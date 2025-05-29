package data;

import user.User;
import java.util.Arrays;
import java.util.List;

public class UserData {
    // Example user list (in a real app, this would come from DB)
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Alice Johnson", "alice@email.com", "https://myapp.com/cv/alice-johnson.pdf"),
            new User(2, "Bob Smith", "bob@email.com", null) // No CV yet
    );

    // For convenience, get a sample current user (CHANGE TO id = 2 => Bob, no CV)
    public static User getCurrentUser() {
        return USERS.get(1); // Now returns Bob, who has no CV
    }
}