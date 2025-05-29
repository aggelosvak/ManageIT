package JobApply;

import data.JobListingData;
import data.UserData;
import model.JobListing;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class JobListingsPage extends JPanel {

    private final JTextArea detailsArea;
    private final JButton applyButton;
    private JobListing selectedListing;

    public JobListingsPage() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Available Job Listings");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Load job listings
        List<JobListing> listings = JobListingData.all();
        DefaultListModel<JobListing> listModel = new DefaultListModel<>();
        for (JobListing jl : listings) {
            listModel.addElement(jl);
        }

        // Show only minimal info in the list: Position + Open/Closed
        JList<JobListing> listingsJList = new JList<>(listModel);
        listingsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listingsJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof JobListing) {
                    JobListing jl = (JobListing) value;
                    setText(jl.getJobPosition().getTitle() + " (" + (jl.isOpen() ? "Open" : "Closed") + ")");
                }
                return this;
            }
        });
        add(new JScrollPane(listingsJList), BorderLayout.CENTER);

        // Details panel, hidden until selection
        detailsArea = new JTextArea(7, 30);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Apply button (initially disabled)
        applyButton = new JButton("Apply for this Job");
        applyButton.setEnabled(false);
        applyButton.addActionListener((ActionEvent e) -> {
            if (selectedListing != null) {
                User currentUser = UserData.getCurrentUser();
                String cvLink = currentUser.getCvLink();
                if (cvLink == null || cvLink.isEmpty()) {
                    // Show upload CV form instead of just warning
                    UploadCVForm uploadCVForm = new UploadCVForm();
                    int result = JOptionPane.showConfirmDialog(
                            this, uploadCVForm, "CV Required", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                    );
                    if (result == JOptionPane.OK_OPTION) {
                        String newCvLink = uploadCVForm.getCvLink();
                        if (newCvLink == null || newCvLink.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "CV link cannot be empty.",
                                    "CV Upload Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        // In a real app, update in DB, here set in memory:
                        currentUser.setCvLink(newCvLink);
                        JOptionPane.showMessageDialog(
                                this,
                                "Your CV has been uploaded. Please try applying again.",
                                "CV Uploaded",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    return;
                }

                ApplicationForm form = new ApplicationForm(currentUser, selectedListing);
                int result = JOptionPane.showConfirmDialog(
                        this, form, "Job Application Form", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );
                if (result == JOptionPane.OK_OPTION) {
                    // All application creation and printing handled in the form:
                    form.submitApplication();

                    JOptionPane.showMessageDialog(
                            this,
                            "Application submitted successfully!",
                            "Application Sent",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JLabel("Details:"), BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        detailsPanel.add(applyButton, BorderLayout.SOUTH);

        add(detailsPanel, BorderLayout.SOUTH);

        // Listen to selection changes
        listingsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedListing = listingsJList.getSelectedValue();
                if (selectedListing != null) {
                    detailsArea.setText(formatListingDetails(selectedListing));
                    applyButton.setEnabled(true);
                } else {
                    detailsArea.setText("");
                    applyButton.setEnabled(false);
                }
            }
        });

        // Show message in details panel before selection
        detailsArea.setText("Select a job from the list to see full details and apply.");
    }

    private String formatListingDetails(JobListing jl) {
        return String.format(
                "ID:          %d\n" +
                        "Position:    %s\n" +
                        "Posted Date: %s\n" +
                        "Open:        %s\n" +
                        "Notes:       %s\n" +
                        "Manager ID:  %d",
                jl.getId(),
                jl.getJobPosition().getTitle(),
                jl.getPostingDate(),
                jl.isOpen() ? "Yes" : "No",
                jl.getNotes() == null ? "" : jl.getNotes(),
                jl.getManagerId()
        );
    }
}