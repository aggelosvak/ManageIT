package data;

import model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomData {
    private static final List<Room> rooms = new ArrayList<>(
            List.of(
                    new Room(1, "Αίθουσα 1", 1, true),
                    new Room(2, "Αίθουσα 2", 1, true),
                    new Room(3, "Αίθουσα 1", 2, true),
                    new Room(4, "Αίθουσα 2", 2, false)
            )
    );

    public static List<Room> getRoomsByCompanyId(int companyId) {
        return rooms.stream().filter(r -> r.getCompanyId() == companyId).collect(Collectors.toList());
    }

    public static List<Room> getAvailableRooms(int companyId) {
        return rooms.stream().filter(r -> r.getCompanyId() == companyId && r.isAvailable()).collect(Collectors.toList());
    }

    public static void setRoomAvailability(int roomId, boolean availability) {
        for (Room r : rooms) {
            if (r.getId() == roomId) {
                r.setAvailable(availability);
            }
        }
    }

    public static Room getRoomById(int id) {
        return rooms.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }
}