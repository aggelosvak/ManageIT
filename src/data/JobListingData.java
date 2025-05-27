package data;

import model.JobListing;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class JobListingData {
    public static final JobListing SERVER_LISTING = new JobListing(
            1,
            JobPositionData.CAFETERIA_SERVER,
            LocalDate.now().minusDays(5),
            true,
            "Morning shift, part-time"
    );

    public static final JobListing COOK_LISTING = new JobListing(
            2,
            JobPositionData.CAFETERIA_COOK,
            LocalDate.now().minusDays(2),
            true,
            "Experience required"
    );

    public static List<JobListing> all() {
        return Arrays.asList(SERVER_LISTING, COOK_LISTING);
    }
}