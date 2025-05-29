package data;

import model.JobListing;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class JobListingData {
    // Example manager IDs (replace 101, 102 with actual manager IDs from your system)
    public static final JobListing SERVER_LISTING = new JobListing(
            1,
            JobPositionData.CAFETERIA_SERVER,
            LocalDate.now().minusDays(5),
            true,
            "Morning shift, part-time",
            101 // <-- Example manager ID
    );

    public static final JobListing COOK_LISTING = new JobListing(
            2,
            JobPositionData.CAFETERIA_COOK,
            LocalDate.now().minusDays(2),
            true,
            "Experience required",
            102 // <-- Example manager ID
    );

    public static List<JobListing> all() {
        return Arrays.asList(SERVER_LISTING, COOK_LISTING);
    }
}