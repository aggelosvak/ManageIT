package data;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import model.Candidate;
import model.JobListing;

// This class provides dummy candidates for a job listing.
public class CandidateData {

    // Now associates candidates with specific job listings for demonstration.
    private static final List<Candidate> DUMMY_CANDIDATES = new ArrayList<>();

    // Must call this to initialize dummy candidates for demo!
    public static void initDemoCandidates(List<JobListing> jobListings) {
        DUMMY_CANDIDATES.clear();
        if (jobListings == null || jobListings.isEmpty()) return;
        // Assign candidates only to the first two listings for demonstration.
        if (jobListings.size() > 0) {
            DUMMY_CANDIDATES.add(new Candidate("c1", "Γιώργος Παπαδόπουλος", "george@email.com", "6912345678", "Σύντομο βιογραφικό εδώ.", jobListings.get(0)));
        }
        if (jobListings.size() > 1) {
            DUMMY_CANDIDATES.add(new Candidate("c2", "Μαρία Νικολάου", "maria@email.com", "6987654321", "Διαθέσιμο βιογραφικό PDF.", jobListings.get(1)));
        }
    }

    // Returns only candidates that applied to the given job listing
    public static List<Candidate> getCandidatesForJob(JobListing listing) {
        return DUMMY_CANDIDATES.stream()
                .filter(candidate -> candidate.getJobListing() != null && candidate.getJobListing().getId() == listing.getId())
                .collect(Collectors.toList());
    }
}