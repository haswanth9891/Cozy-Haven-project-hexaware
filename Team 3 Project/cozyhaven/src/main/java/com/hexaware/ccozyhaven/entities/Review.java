package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "Review_Details")
public class Review {

    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(name = "rating")
    private int rating;

    @NotBlank(message = "Review text is required")
    @Size(max = 255, message = "Review text must be at most 255 characters")
    @Column(name = "review_text")
    private String reviewText;

    @NotNull(message = "Review date is required")
    @Column(name = "review_date")
    private Date reviewDate;

    public Review() {
        super();
    }

    public Review(Long reviewId, int rating, String reviewText, Date reviewDate) {
        super();
        this.reviewId = reviewId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

     

    public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Override
    public String toString() {
        return "Review [reviewId=" + reviewId + ", rating=" + rating + ", reviewText=" + reviewText + ", reviewDate="
                + reviewDate + "]";
    }
}