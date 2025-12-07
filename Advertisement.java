public class Advertisement {

    // Attributes
    private String id;
    private String title;
    private String description;
    private double price;
    private String category;

    private RegisteredUser owner;   // the user who created the ad
    private boolean isApproved;
    private boolean isSold;

    // Constructor
    public Advertisement(String id, String title, String description, double price,
                         String category, RegisteredUser owner) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.owner = owner;

        this.isApproved = false; // default
        this.isSold = false;     // default
    }

    // Old detailed display
    public void displayDetails() {
        System.out.println(toString());
    }

    public void markAsSold() {
        this.isSold = true;
        System.out.println("Advertisement marked as SOLD.");
    }

    // --- GETTERS & SETTERS ---

    public String getId() {
        return id;
    }

    public RegisteredUser getOwner() {
        return owner;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }

    public boolean isSold() {
        return isSold;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ad ID: " + id
                + " | Title: " + title
                + " | Price: " + price
                + " | Category: " + category
                + " | Owner: " + owner.getUsername()
                + " | Approved: " + isApproved
                + " | Sold: " + isSold;
    }
}
