package hiring;

import model.JobListing;
import model.Candidate;
import data.CandidateData;
import data.JobListingData;
import notification.Notification;
import company.Company;
import data.CompanyData;
import employee.Employee;
import model.JobPosition;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class JobListingHiringPage extends JFrame {
    private JobListing jobListing;
    private DefaultListModel<Candidate> candidateListModel;
    private JList<Candidate> candidateJList;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public JobListingHiringPage(JobListing jobListing) {
        this.jobListing = jobListing;

        // --- IMPORTANT: Initialize dummy candidates if not already done ---
        // This ensures the candidate list is filled for demo purposes
        CandidateData.initDemoCandidates(JobListingData.all());
        // --- END IMPORTANT ---

        setTitle("Πρόσληψη Υπαλλήλου");
        setSize(550, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createCandidateSelectionPanel(), "candidates");
        cardPanel.add(createHireFormPanel(), "form");

        add(cardPanel);
        cardLayout.show(cardPanel, "candidates");
    }

    private JPanel createCandidateSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String title = (jobListing.getJobPosition() != null) ? jobListing.getJobPosition().getTitle() : "";
        String description = (jobListing.getJobPosition() != null) ? jobListing.getJobPosition().getDescription() : "";

        JLabel listingInfo = new JLabel(
                "<html><b>Αγγελία:</b> " + title  + "<br/>"
                        + "<b>Περιγραφή:</b> " + description + "</html>"
        );
        panel.add(listingInfo, BorderLayout.NORTH);

        candidateListModel = new DefaultListModel<>();
        // Just get the candidates with the provided API
        List<Candidate> candidates = CandidateData.getCandidatesForJob(jobListing);
        for (Candidate c : candidates) candidateListModel.addElement(c);

        candidateJList = new JList<>(candidateListModel);
        candidateJList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getName() + " (" + value.getEmail() + ")");
            label.setOpaque(true);
            if (isSelected) label.setBackground(new Color(220, 235, 255));
            return label;
        });

        panel.add(new JScrollPane(candidateJList), BorderLayout.CENTER);

        JButton nextButton = new JButton("Συνέχεια");
        nextButton.addActionListener(e -> {
            if (candidateJList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Επιλέξτε έναν υποψήφιο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cardLayout.show(cardPanel, "form");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHireFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        String title = (jobListing.getJobPosition() != null) ? jobListing.getJobPosition().getTitle() : "";
        String description = (jobListing.getJobPosition() != null) ? jobListing.getJobPosition().getDescription() : "";

        JLabel jobInfoLabel = new JLabel(
                "<html><b>Θέση:</b> " + title + "<br/>"
                        + "<b>Περιγραφή:</b> " + description + "</html>"
        );
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(jobInfoLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Όνομα υποψήφιου:"), gbc);

        JTextField candidateNameTF = new JTextField(20);
        candidateNameTF.setEditable(false);
        gbc.gridx = 1; panel.add(candidateNameTF, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Μισθός:"), gbc);

        JTextField salaryTF = new JTextField(10);
        gbc.gridx = 1; panel.add(salaryTF, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Σχόλια/Παρατηρήσεις:"), gbc);

        JTextField commentsTF = new JTextField(20);
        gbc.gridx = 1; panel.add(commentsTF, gbc);

        JButton hireBtn = new JButton("Πρόσληψη");
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(hireBtn, gbc);

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                Candidate selected = candidateJList.getSelectedValue();
                if (selected != null) {
                    candidateNameTF.setText(selected.getName());
                } else {
                    candidateNameTF.setText("");
                }
            }
        });

        hireBtn.addActionListener(e -> {
            String salaryStr = salaryTF.getText().trim();
            if (salaryStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Συμπληρώστε μισθό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            } else {
                Candidate selected = candidateJList.getSelectedValue();
                double salary = 0.0;
                try {
                    salary = Double.parseDouble(salaryStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ο μισθός πρέπει να είναι αριθμός.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int newEmployeeId = (int) (System.currentTimeMillis() % 1_000_000);
                Company company = null;
                int compId = -1;
                if (jobListing.getJobPosition() != null) {
                    compId = jobListing.getJobPosition().getCompanyId();
                }
                if (compId > 0) {
                    company = CompanyData.getCompanyById(compId);
                }
                JobPosition jobPosition = jobListing.getJobPosition();
                Employee employee = new Employee(newEmployeeId, selected.getName(), company, jobPosition);
                employee.setSalary(salary);

                int requestId = (int) (System.nanoTime() % 1_000_000);
                int userId = 0;
                try {
                    userId = Integer.parseInt(selected.getId().replaceAll("\\D", ""));
                } catch (NumberFormatException ignore) {
                    userId = newEmployeeId;
                }
                int jobId = jobListing.getId();
                String message = "Πρόσληψη υποψήφιου " + selected.getName() + " για θέση " + (jobPosition != null ? jobPosition.getTitle() : "");
                JobRequest jobRequest = new JobRequest(requestId, userId, jobId, message);

                int notifId = (int)(System.nanoTime() % 1_000_000);
                int receiverId = jobListing.getManagerId();
                LocalDateTime now = LocalDateTime.now();
                String notifTitle = "Νέα Πρόσληψη";
                String notifMsg = "Ο/Η υποψήφιος " + selected.getName() + " προσλήφθηκε στη θέση \"" +
                        (jobPosition != null ? jobPosition.getTitle() : "") + "\" με μισθό " + salary + ".";
                Notification notification = new Notification(
                        notifId, now, receiverId, notifTitle, notifMsg, "EMPLOYEE_HIRE", "HIGH"
                );

                System.out.println("=== ΝΕΑ ΠΡΟΣΛΗΨΗ ΥΠΑΛΛΗΛΟΥ ===");
                System.out.println("Employee: " + employee);
                System.out.println("JobRequest: " + jobRequest);
                System.out.println("Notification:");
                notification.displayNotification();

                JOptionPane.showMessageDialog(this,
                        "Επιτυχής πρόσληψη για τον/την: " + candidateNameTF.getText() +
                                "\nΜισθός: " + salaryStr,
                        "Επιτυχία", JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
        });

        return panel;
    }

}