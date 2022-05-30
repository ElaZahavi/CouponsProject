package ela.johnbryce.CouponsProjectSpring.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "coupons")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Company company;
	@Column
	private int amount;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String image;
	@Column
	@Enumerated(EnumType.STRING)
	private CategoryType category;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private double price;
	
	public Coupon(int id, Company company, int amount, String title, String description, String image,
			CategoryType category, Date startDate, Date endDate, double price) {
		super();
		this.id = id;
		this.company = company;
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.image = image;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	public Coupon(int amount, String title, String description, String image, CategoryType category,
			Date startDate, Date endDate, double price) {
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.image = image;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	public Coupon() {}

	public int getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}
	
	//notice when update coupon
	public void setCompany(Company company) {
		this.company = company;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coupon)) {
			return false;
		} else {
			return this.id == ((Coupon)obj).id;
		}
	}
	
	@Override
	public int hashCode() {
		return id * title.hashCode();
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", company=" + company + ", amount=" + amount + ", title=" + title
				+ ", description=" + description + ", image=" + image + ", category=" + category + ", startDate="
				+ startDate + ", endDate=" + endDate + ", price=" + price + "]";
	}
}