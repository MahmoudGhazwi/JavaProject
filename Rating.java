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
        System.out.println(this);  // استخدام toString
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

    @Override
    public String toString() {
        return "Score: " + score
                + " | Rated By: " + ratedBy.getUsername()
                + " | Rated User: " + ratedUser.getUsername()
                + " | Comment: " + comment
                + " | Transaction ID: " + transaction.getId();
    }
}
