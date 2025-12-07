import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static List<Advertisement> allAds = new ArrayList<>();
    private static List<Transaction> allTransactions = new ArrayList<>();
    private static List<RegisteredUser> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();

    private static int adCounter = 1;   // A1, A2, A3...

    public static void main(String[] args) {

        // Sample Data
        RegisteredUser seller = new RegisteredUser("U1", "seller123", "pass1");
        RegisteredUser buyer  = new RegisteredUser("U2", "buyer123", "pass2");
        Admin admin           = new Admin("A1", "admin", "admin123");

        Advertisement ad1 = new Advertisement("A1", "Sample Product", "Example description", 100, "General", seller);
        ad1.setApproved(true);

        allAds.add(ad1);
        users.add(seller);
        users.add(buyer);
        admins.add(admin);

        System.out.println("=== Marketplace System ===");

        while (true) {
            User currentUser = login();

            if (currentUser == null) {
                System.out.println("Too many failed attempts. Exiting...");
                break;
            }

            if (currentUser instanceof Admin) {
                runAdminMenu((Admin) currentUser);
            } else {
                runUserMenu((RegisteredUser) currentUser);
            }
        }

        scanner.close();
    }


    // ====================== LOGIN ======================

    private static User login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("\n=== Login ===");
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Check registered users (fix: check ID first then ban then password)
            for (RegisteredUser user : users) {

                if (user.getId().equals(id)) {

                    if (user.isBanned()) {
                        System.out.println("This user is banned!");
                        return null;
                    }

                    if (user.login(id, password)) {
                        System.out.println("Logged in as Registered User: " + user.getUsername());
                        return user;
                    }
                }
            }

            // Check admins
            for (Admin admin : admins) {
                if (admin.login(id, password)) {
                    System.out.println("Logged in as Admin: " + admin.getUsername());
                    return admin;
                }
            }

            System.out.println("Invalid ID or password.");
            attempts++;
        }

        return null;
    }


    // ====================== USER MENU ======================

    private static void runUserMenu(RegisteredUser currentUser) {
        int choice;

        do {
            System.out.println("\n===== User Menu =====");
            System.out.println("Logged in as: " + currentUser.getUsername());
            System.out.println("1) View available advertisements");
            System.out.println("2) Show advertisement details");
            System.out.println("3) Purchase an advertisement");
            System.out.println("4) Post a new advertisement");
            System.out.println("5) Show my advertisements");
            System.out.println("6) Delete my advertisement");
            System.out.println("0) Logout");
            System.out.print("Choose: ");

            choice = readInt();

            switch (choice) {
                case 1 -> listAdsShort();
                case 2 -> showDetails();
                case 3 -> purchaseAd(currentUser);
                case 4 -> postNewAd(currentUser);
                case 5 -> showUserAds(currentUser);
                case 6 -> deleteUserAd(currentUser);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }


    // ====================== ADMIN MENU ======================

    private static void runAdminMenu(Admin admin) {
        int choice;

        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("Logged in as: " + admin.getUsername());
            System.out.println("1) View all advertisements");
            System.out.println("2) View unapproved advertisements");
            System.out.println("3) Approve advertisement");
            System.out.println("4) Ban a user");
            System.out.println("0) Logout");
            System.out.print("Choose: ");

            choice = readInt();

            switch (choice) {
                case 1 -> listAllAds();
                case 2 -> admin.viewUnapprovedAds(allAds);
                case 3 -> approveAd(admin);
                case 4 -> banUser();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }


    // ====================== USER OPERATIONS ======================

    private static void postNewAd(RegisteredUser owner) {
        System.out.println("\n=== Post a New Advertisement ===");

        String id = "A" + adCounter++;
        System.out.println("Generated ID: " + id);

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Price: ");
        double price = readDouble();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        Advertisement ad = new Advertisement(id, title, description, price, category, owner);
        owner.postAd(ad);
        allAds.add(ad);
    }

    private static void showUserAds(RegisteredUser currentUser) {
        List<Advertisement> myAds = currentUser.myAds();

        if (myAds.isEmpty()) {
            System.out.println("You have no ads.");
            return;
        }

        System.out.println("\n=== My Advertisements ===");
        for (Advertisement ad : myAds) {
            ad.displayDetails();
            System.out.println("Approved: " + ad.isApproved());
            System.out.println("Sold: " + ad.isSold());
        }
    }

    private static void deleteUserAd(RegisteredUser currentUser) {
        System.out.print("Enter Ad ID to delete: ");
        String id = scanner.nextLine();

        currentUser.deleteAd(id);

        Advertisement ad = findAdById(id);
        if (ad != null && ad.getOwner() == currentUser) {
            allAds.remove(ad);
        }
    }

    private static void showDetails() {
        System.out.print("Enter Ad ID: ");
        String id = scanner.nextLine();

        Advertisement ad = findAdById(id);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (!ad.isApproved()) {
            System.out.println("This ad is not approved.");
            return;
        }

        ad.displayDetails();
    }


    private static void purchaseAd(RegisteredUser buyer) {
        System.out.print("Enter Ad ID: ");
        String id = scanner.nextLine();

        Advertisement ad = findAdById(id);

        if (ad == null || !ad.isApproved() || ad.isSold()) {
            System.out.println("Ad not available.");
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

        createRatingForTransaction(buyer, t);
    }


    // ====================== RATING ======================

    private static void createRatingForTransaction(RegisteredUser buyer, Transaction transaction) {

        RegisteredUser seller = transaction.getSeller();

        System.out.println("\n=== Rate the Seller ===");
        System.out.println("Product: " + transaction.getAd().getTitle());
        System.out.println("Seller: " + seller.getUsername());

        int score;
        while (true) {
            System.out.print("Rating (1-5): ");
            score = readInt();
            if (score >= 1 && score <= 5) break;
            System.out.println("Invalid rating.");
        }

        System.out.print("Comment: ");
        String comment = scanner.nextLine();

        Rating rating = new Rating(buyer, seller, transaction, score, comment);

        rating.applyRating();
        seller.addRating(rating);
    }


    // ====================== ADMIN OPERATIONS ======================

    private static void approveAd(Admin admin) {
        System.out.print("Enter Ad ID to approve: ");
        String id = scanner.nextLine();

        Advertisement ad = findAdById(id);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (ad.isApproved()) {
            System.out.println("Already approved.");
            return;
        }

        admin.approveAd(ad);
    }

    private static void listAllAds() {
        System.out.println("\n=== All Advertisements ===");

        if (allAds.isEmpty()) {
            System.out.println("No ads available.");
            return;
        }

        for (Advertisement ad : allAds) {
            ad.displayDetails();
            System.out.println("Approved: " + ad.isApproved());
            System.out.println("Sold: " + ad.isSold());
        }
    }

    private static void banUser() {
        System.out.println("\n=== Ban User ===");

        for (RegisteredUser user : users) {
            System.out.println("ID: " + user.getId() + " | Username: " + user.getUsername());
        }

        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        for (RegisteredUser u : users) {
            if (u.getId().equals(id)) {
                if (u.isBanned()) {
                    System.out.println("Already banned.");
                } else {
                    u.setBanned(true);
                    System.out.println("User banned successfully.");
                }
                return;
            }
        }

        System.out.println("User not found.");
    }


    // ====================== HELPERS ======================

    private static Advertisement findAdById(String id) {
        for (Advertisement ad : allAds) {
            if (ad.getId().equals(id)) return ad;
        }
        return null;
    }

    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int x = scanner.nextInt();
        scanner.nextLine();
        return x;
    }

    private static double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        double x = scanner.nextDouble();
        scanner.nextLine();
        return x;
    }
}
