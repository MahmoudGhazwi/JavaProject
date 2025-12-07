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

    // Post new Ad (Done)
    public void postAd(Advertisement ad) {
        ads.add(ad); 
        System.out.println("Ad posted successfully!");
        System.out.println("pending admin approval.");
    }
    
    // View user ads (Done)
    public List<Advertisement> myAds() {
        return ads;
    }

    // Edit advertisement (placeholder)
    public void editAd(String adId) {
        System.out.println("Editing ad with ID: " + adId);
    }

    // Delete advertisement (Done)
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
    
    // getter
    public List<Rating> getRatings() {
        return ratings;
    }
    
    // Solving average rating
    public double avgRating() {

        if (ratings.isEmpty()) {
            return 0.0; // no ratings yet
        }

        int sum = 0;

        for (Rating r : ratings) {
            sum += r.getScore();
        }

        return (double) sum / ratings.size();
    }

    // banned check
    public boolean isBanned() {
        return isBanned;
    }

    // change banned status
    public void setBanned(boolean banned) {
        isBanned = banned;
    }
    
    // View user info to other users
    public String getInfo() {
        return "Username: " + getUsername()
            + " | Ratings: " + ratings.size()
            + " | Average Rating: " + String.format("%.2f", avgRating());
    }

    @Override
    public String toString() {
        return "User ID: " + getId()
            + " | Username: " + getUsername()
            + " | Ads Posted: " + ads.size()
            + " | Ratings: " + ratings.size()
            + " | Average Rating: " + String.format("%.2f", avgRating())
            + " | Banned: " + isBanned;
    }
}
