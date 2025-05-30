import javax.swing.*;
import java.awt.*;
import java.util.List;

import data.JobListingData;
import data.CompanyData;
import company.Company;
import model.JobListing;
import hiring.JobListingHiringPage;

public class JobListingsManagerPage extends JFrame {
    private DefaultListModel<String> listingsModel = new DefaultListModel<>();
    private JList<String> listingsList;

    public JobListingsManagerPage() {
        setTitle("Διαχείριση Αγγελιών");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listingsList = new JList<>(listingsModel);
        refreshListings();

        JButton postBtn = new JButton("Ανάρτηση Αγγελίας");
        postBtn.addActionListener(e -> {
            Company demoCompany = CompanyData.getCompanyById(1);
            new JobListingUpload.JobListingUploadPage(demoCompany).setVisible(true);
        });

        JButton hireEmployeeBtn = new JButton("Πρόσληψη Υπαλλήλου");
        hireEmployeeBtn.addActionListener(e -> {
            int selectedIdx = listingsList.getSelectedIndex();
            if (selectedIdx != -1) {
                JobListing selected = JobListingData.all().get(selectedIdx);
                new JobListingHiringPage(selected).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε μια αγγελία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(postBtn);
        btnPanel.add(hireEmployeeBtn);

        setLayout(new BorderLayout());
        add(new JLabel("Διαθέσιμες Αγγελίες:"), BorderLayout.NORTH);
        add(new JScrollPane(listingsList), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshListings() {
        listingsModel.clear();
        List<JobListing> jobListings = JobListingData.all();
        for (JobListing jl : jobListings) listingsModel.addElement(jl.toString());
    }
}