import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {

    // Attributes
    private boolean isBanned;
    private List<Advertisement> ads;
    private List<Rating> ratings;   // ratings received
    
    // Constructor
    public RegisteredUser(String id, String username, String password) {
        super(id, username, password);
        this.isBanned = false; // Default
        this.ads = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    // --- METHODS FROM UML ---

    // Post new Ad ++++++
    public void postAd(Advertisement ad) {
        ads.add(ad); 
        System.out.println("Ad posted successfully!");
        System.out.println("pending admin approval.");
    }
    
    // View user ads ++++++
    public List<Advertisement> myAds() {
        return ads;
    }

    // Edit advertisement ------
    public void editAd(String adId) {
        System.out.println("Editing ad with ID: " + adId);
    }

    // Delete advertisement ++++++
    public void deleteAd(String adId) {

    Advertisement target = null;

    for (Advertisement ad : ads) {
        if (ad.getId().equals(adId)) {
            target = ad;
            break;
        }
    }

    if (target == null) {
        System.out.println("No advertisement found with this ID.");
        return;
    }

    ads.remove(target);
    System.out.println("Advertisement " + adId + " deleted successfully.");
    }

    // Add rating
    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    // Extra getters and setters
    public List<Rating> getRatings() {
        return ratings;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
