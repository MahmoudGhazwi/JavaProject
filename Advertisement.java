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

    // --- METHODS FROM UML ---

    public void displayDetails() {
        System.out.println("Ad ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Category: " + category);
        System.out.println("Owner: " + owner.getUsername());
        System.out.println("Approved: " + isApproved);
        System.out.println("Sold: " + isSold);
        System.out.println("-----------------------------------");
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
}
