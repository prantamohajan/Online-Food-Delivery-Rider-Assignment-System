import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryManager {
    private ArrayList<Order> orderList;
    private HashMap<String, Rider> riderMap;

    public DeliveryManager() {
        this.orderList = new ArrayList<>();
        this.riderMap = new HashMap<>();
    }

    // Add Rider to HashMap
    public void addRider(Rider rider) {
        riderMap.put(rider.getId(), rider);
    }

    // Add Order to List
    public void addOrder(Order order) {
        orderList.add(order);
    }

    // Assign first available matching rider
    public Rider assignRider(Order order) throws NoRiderAvailableException {
        for (Rider rider : riderMap.values()) {
            if (rider.isAvailable()) {
                rider.assignOrder(order);
                return rider;
            }
        }
        throw new NoRiderAvailableException("No riders are currently available to take Order #" + order.getOrderId());
    }

    // Complete order by Rider ID
    public boolean completeOrder(String riderId) {
        Rider rider = riderMap.get(riderId);
        if (rider != null && !rider.isAvailable()) {
            rider.completeOrder();
            return true;
        }
        return false;
    }

    // Method Overloading: Search by ID
    public Rider searchRider(String id) {
        return riderMap.get(id);
    }

    // Method Overloading: Search by Vehicle Type
    public Rider searchRider(String vehicleType, boolean onlyAvailable) {
        for (Rider rider : riderMap.values()) {
            if (rider.getVehicleType().equalsIgnoreCase(vehicleType) && (!onlyAvailable || rider.isAvailable())) {
                return rider;
            }
        }
        return null;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public HashMap<String, Rider> getRiderMap() {
        return riderMap;
    }
}