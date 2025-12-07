import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static List<Advertisement> allAds = new ArrayList<>();
    private static List<Transaction> allTransactions = new ArrayList<>();

    private static List<RegisteredUser> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();

    // Auto ID counter for advertisements: A1, A2, A3, ...
    private static int adCounter = 1;

    public static void main(String[] args) {

        // Sample users
        RegisteredUser seller = new RegisteredUser("U1", "seller123", "pass1");
        RegisteredUser buyer  = new RegisteredUser("U2", "buyer123", "pass2");
        Admin admin           = new Admin("A1", "admin", "admin123");

        Advertisement ad1 = new Advertisement("A1", "title", "description", 100, "category", seller);
        ad1.setApproved(true);
        allAds.add(ad1);

        users.add(seller);
        users.add(buyer);
        admins.add(admin);

        System.out.println("=== Simple Marketplace System ===");

        while (true) {
            User currentUser = login();
            if (currentUser == null) {
                System.out.println("Too many failed attempts. Exiting program...");
                break;
            }

            if (currentUser instanceof Admin) {
                runAdminMenu((Admin) currentUser);
            } else if (currentUser instanceof RegisteredUser) {
                runUserMenu((RegisteredUser) currentUser);
            }
        }

        scanner.close();
    }

    // ---------- LOGIN BY ID + PASSWORD USING User.login() ----------

    private static User login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("\n=== Login ===");
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Check registered users
            for (RegisteredUser user : users) {

                if (user.login(id, password)) {
                    if (user.isBanned()){
                        System.out.println("This user is Banned!");
                        return null;
                    }
                    System.out.println("Logged in as Registered User: " + user.getUsername());
                    return user;
                }
            }

            // Check admins
            for (Admin admin : admins) {
                if (admin.login(id, password)) {
                    System.out.println("Logged in as Admin: " + admin.getUsername());
                    return admin;
                }
            }

            System.out.println("Invalid ID or password. Try again.");
            attempts++;
        }

        return null; // failed login
    }

    // ---------- USER MENU (RegisteredUser) ----------

    private static void runUserMenu(RegisteredUser currentUser) {
        int choice;

        do {
            System.out.println("\n===== User Menu =====");
            System.out.println("Logged in as: " + currentUser.getUsername());
            System.out.println("1) View available advertisements");
            System.out.println("2) Show an advertisement details");
            System.out.println("3) Purchase an advertisement");
            System.out.println("4) Post a new advertisement");
            System.out.println("5) Show my advertisements");
            System.out.println("6) Delete my advertisement");
            System.out.println("0) Logout");
            System.out.println("=====================");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    listAdsShort();
                    break;
                case 2:
                    showDetails();
                    break;
                case 3:
                    PurchaseAd(currentUser);
                    break;
                case 4:
                    postNewAd(currentUser);
                    break;
                case 5:
                    showUserAds(currentUser);
                    break;
                case 6:
                    deleteUserAd(currentUser);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);
    }

    // ---------- ADMIN MENU ----------

    private static void runAdminMenu(Admin admin) {
        int choice;

        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("Logged in as: " + admin.getUsername());
            System.out.println("1) View all advertisements");
            System.out.println("2) View unapproved advertisements");
            System.out.println("3) Approve an advertisement");
            System.out.println("4) Banned an user");
            System.out.println("0) Logout");
            System.out.println("======================");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    listAllAds();
                    break;
                case 2:
                    admin.viewUnapprovedAds(allAds);
                    break;
                case 3:
                    ApproveAd(admin);
                    break;
                case 4:
                    bannedUser();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);
    }

    // ---------- USER OPERATIONS ----------

    private static void postNewAd(RegisteredUser owner) {
        System.out.println("\n=== Post a New Advertisement ===");

        // Auto-generate ID: A1, A2, A3, ...
        String id = "A" + adCounter++;
        System.out.println("Generated Ad ID: " + id);

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = readDouble();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        Advertisement ad = new Advertisement(id, title, description, price, category, owner);
        owner.postAd(ad);
        allAds.add(ad);
    }

    private static void showUserAds(RegisteredUser currentUser) {

        System.out.println("\n=== My Advertisements ===");

        List<Advertisement> myAds = currentUser.MyAds();  

        if (myAds.isEmpty()) {
            System.out.println("You have no advertisements.");
            return;
        }

        for (Advertisement ad : myAds) {
            ad.displayDetails();
            System.out.println("Approved: " + ad.isApproved());
            System.out.println("Sold: " + ad.isSold());
        }
        
    }

    private static void deleteUserAd(RegisteredUser currentUser) {
    System.out.print("Enter Ad ID to delete: ");
    String adId = scanner.nextLine();

    currentUser.deleteAd(adId);   //RegisteredUser Deleting ad from user

    Advertisement adToRemove = findAdById(adId);     //Deleting ad from allAds list
    if (adToRemove != null && adToRemove.getOwner() == currentUser) {
        allAds.remove(adToRemove);
    }
}


        // User sees only approved and not sold ads

    /*private static void listAvailableAds() {
        System.out.println("\n=== Available Advertisements ===");

        boolean found = false;

        for (Advertisement ad : allAds) {
            if (ad.isApproved() && !ad.isSold()) {
                ad.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available advertisements to display.");
        }
    }*/

    private static void showDetails() {
        
        System.out.print("\nEnter Ad ID to view details: ");
        String adId = scanner.nextLine();

        Advertisement ad = findAdById(adId);

        if (ad == null) {
            System.out.println("No advertisement found with this ID.");
            return;
        }

        if (!ad.isApproved()) {
            System.out.println("This ad is not approved yet.");
            return;
        }
        
        ad.displayDetails();  //  Show full details
    }

    private static void PurchaseAd(RegisteredUser buyer) {
        System.out.println("\n=== Purchase Advertisement ===");

        System.out.print("Enter Ad ID to purchase: ");
        String adId = scanner.nextLine();

        Advertisement ad = findAdById(adId);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (!ad.isApproved()) {
            System.out.println("Cannot purchase an unapproved ad.");
            return;
        }

        if (ad.isSold()) {
            System.out.println("This ad has already been sold.");
            return;
        }

        Transaction t = new Transaction(
                "T" + (allTransactions.size() + 1),
                buyer,
                ad.getOwner(),
                ad,
                ad.getPrice()
        );

        t.completeTransaction();
        allTransactions.add(t);

        System.out.println("Transaction created: " + t.getId());

        createRatingForTransaction(buyer, t);
    }

    private static void createRatingForTransaction(RegisteredUser buyer, Transaction transaction) {

        RegisteredUser seller = transaction.getSeller();  // get seller from transaction

        System.out.println("\n=== Rate the Seller ===");
        System.out.println("You just bought: " + transaction.getAd().getTitle());
        System.out.println("Seller: " + seller.getUsername());

        int score;
        while (true) {
            System.out.print("Enter rating (1-5): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Enter a number between 1 and 5: ");
                scanner.next();
            }
            score = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (score >= 1 && score <= 5) {
                break;
            }
            System.out.println("Invalid rating. Please enter between 1 and 5.");
        }

        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        Rating rating = new Rating(buyer, seller, transaction, score, comment);

        // Apply rating (print informations)
        rating.applyRating();

        // Add rating to user who received rate
        seller.addRating(rating);
    }

    // ---------- ADMIN OPERATIONS ----------

    private static void ApproveAd(Admin currentAdmin) {
        System.out.println("\n=== Approve Advertisement ===");

        System.out.print("Enter Ad ID to approve: ");
        String adId = scanner.nextLine();

        Advertisement ad = findAdById(adId);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (ad.isApproved()) {
            System.out.println("This ad is already approved.");
        } else {
            currentAdmin.approveAd(ad);
        }
    }

    private static void listAllAds() {
        System.out.println("\n=== All Advertisements (Admin View) ===");

        if (allAds.isEmpty()) {
            System.out.println("No advertisements available.");
            return;
        }

        for (Advertisement ad : allAds) {
            ad.displayDetails();
            System.out.println("Approved: " + ad.isApproved());
            System.out.println("Sold: " + ad.isSold());
        }
    }

    private static void bannedUser() {
        System.out.println("\n=== Ban a User ===");
        
        // Show users
        if (users.isEmpty()) {
            System.out.println("No registered users found.");
            return;
        }

        System.out.println("Registered Users:");
        for (RegisteredUser user : users) {
            System.out.println("ID: " + user.getId() + " | Username: " + user.getUsername());
        }

        System.out.print("\nEnter User ID to ban: ");
        String userId = scanner.nextLine();

        // Searching for user
        RegisteredUser userToBan = null;

        for (RegisteredUser user : users) {
            if (user.getId().equals(userId)) {
                userToBan = user;
                break;
            }
        }

        if (userToBan == null) {
            System.out.println("No user found with this ID.");
            return;
        }

        // If already banned
        if (userToBan.isBanned()) {
            System.out.println("This user is already banned.");
            return;
        }

        // Banned
        userToBan.setBanned(true);
        System.out.println("User " + userToBan.getUsername() + " has been banned successfully.");
    }


    // ---------- SHARED HELPERS ----------
    private static void listAdsShort() {
        System.out.println("\n=== Available Advertisements ===");

        boolean found = false;

        for (Advertisement ad : allAds) {
            if (ad.isApproved() && !ad.isSold()) {
                System.out.println("ID: " + ad.getId() + " | Title: " + ad.getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available advertisements.");
        }
    }

    private static Advertisement findAdById(String id) {
        for (Advertisement ad : allAds) {
            if (ad.getId().equals(id)) {
                return ad;
            }
        }
        return null;
    }

    private static double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return val;
    }
}
