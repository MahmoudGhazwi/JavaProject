import java.util.List;

public class Admin extends User {

    // Constructor
    public Admin(String id, String username, String password) {
        super(id, username, password);
    }

    // --- METHODS FROM UML ---

    // View ads that are not approved yet
    public void viewUnapprovedAds(List<Advertisement> ads) {
        System.out.println("=== Unapproved Advertisements ===");
        for (Advertisement ad : ads) {
            if (!ad.isApproved()) {
                ad.displayDetails();
            }
        }
    }
 
    // Approve an advertisement
    public void approveAd(Advertisement ad) {
        ad.setApproved(true);
        System.out.println("Advertisement " + ad.getId() + " has been approved.");
    }

    // Delete an advertisement
    public void deleteAd(List<Advertisement> ads, String adId) {
        ads.removeIf(ad -> ad.getId().equals(adId));
        System.out.println("Advertisement " + adId + " has been deleted.");
    }

    // Ban a registered user
    public void banUser(RegisteredUser user) {
        user.setBanned(true);
        System.out.println("User " + user.getUsername() + " has been banned.");
    }
}
