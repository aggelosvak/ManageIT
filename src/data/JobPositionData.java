package data;

import model.JobPosition;

public class JobPositionData {
    public static final JobPosition CAFETERIA_SERVER = new JobPosition(
            1,
            "Cafeteria Server",
            "Serve food to customers, keep dining area clean.",
            true,
            10.50
    );

    public static final JobPosition CAFETERIA_COOK = new JobPosition(
            2,
            "Cafeteria Cook",
            "Prepare and cook food items according to menu.",
            true,
            13.00
    );
}