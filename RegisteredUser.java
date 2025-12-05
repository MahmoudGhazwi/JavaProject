import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {

    // Attributes
    private boolean isBanned;
    private List<Advertisement> ads;

    // Constructor
    public RegisteredUser(String id, String username, String password) {
        super(id, username, password);
        this.isBanned = false; // Default
        this.ads = new ArrayList<>();
    }

    // --- METHODS FROM UML ---

    // Post new Ad ++++++
    public void postAd(Advertisement ad) {
        if (!isBanned) {
            ads.add(ad);
            System.out.println("Ad posted successfully!");
        } else {
            System.out.println("User is banned and cannot post ads.");
        }
    }
    
    // View user ads ++++++
    public List<Advertisement> viewMyAds() {
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


    // Search advertisements --- To main
    public List<Advertisement> searchAds(String keyword) {
        System.out.println("Searching ads with keyword: " + keyword);
        return new ArrayList<>(); // dummy return for now
    }

    // View advertisement details --- To main
    public void viewAdDetails(String adId) {
        System.out.println("Viewing details of ad: " + adId);
    }

    // Purchase an advertisement --- To main
    public Transaction purchaseAd(String adId) {
        System.out.println("Purchasing ad with ID: " + adId);
        return new Transaction();
    }

    // Extra getters and setters

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
