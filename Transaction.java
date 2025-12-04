public class Transaction {

    // Attributes
    private String id;
    private RegisteredUser buyer;
    private RegisteredUser seller;
    private Advertisement ad;
    private double finalPrice;
    private String status;   // e.g. "Pending", "Completed"

    // Constructor
    public Transaction(String id, RegisteredUser buyer, RegisteredUser seller, Advertisement ad, double finalPrice) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.ad = ad;
        this.finalPrice = finalPrice;
        this.status = "Pending";
    }

    // Default constructor (if needed for temporary creation)
    public Transaction() {
        this.status = "Pending";
    }

    // --- METHOD FROM UML ---
    public void completeTransaction() {
        if (!ad.isSold()) {
            ad.markAsSold();
            status = "Completed";
            System.out.println("Transaction completed successfully!");
        } else {
            System.out.println("Transaction failed: Item already sold.");
        }
    }

    // --- GETTERS ---

    public String getId() {
        return id;
    }

    public RegisteredUser getBuyer() {
        return buyer;
    }

    public RegisteredUser getSeller() {
        return seller;
    }

    public Advertisement getAd() {
        return ad;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public String getStatus() {
        return status;
    }
}
