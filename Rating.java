public class Rating {

    // Attributes
    private RegisteredUser ratedBy;     // who gave the rating
    private RegisteredUser ratedUser;   // who receives the rating
    private Transaction transaction;    // the transaction related to this rating
    private int score;                  // rating score (e.g., 1–5)
    private String comment;

    // Constructor
    public Rating(RegisteredUser ratedBy, RegisteredUser ratedUser,
                  Transaction transaction, int score, String comment) {

        this.ratedBy = ratedBy;
        this.ratedUser = ratedUser;
        this.transaction = transaction;
        this.score = score;
        this.comment = comment;
    }

    // --- METHOD FROM UML ---
    public void applyRating() {
        System.out.println("Rating applied successfully:");
        System.out.println("Rated By: " + ratedBy.getUsername());
        System.out.println("Rated User: " + ratedUser.getUsername());
        System.out.println("Score: " + score);
        System.out.println("Comment: " + comment);
        System.out.println("Transaction ID: " + transaction.getId());
    }

    // --- GETTERS ---
    public RegisteredUser getRatedBy() {
        return ratedBy;
    }

    public RegisteredUser getRatedUser() {
        return ratedUser;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }
}
