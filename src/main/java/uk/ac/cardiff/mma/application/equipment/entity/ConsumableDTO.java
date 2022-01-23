package uk.ac.cardiff.mma.application.equipment.entity;

import javax.persistence.*;

@Entity
@Table(name="consumable")
public class ConsumableDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;

	@Column
	private String type;
	
	@Column
	private int stock;




	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	protected ConsumableDTO() {}

	public ConsumableDTO(String name, String description, String type, int stock) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.stock = stock;
	}


	public ConsumableDTO(String name, String description, int stock){
		super();
		this.name = name;
		this.description = description;
		this.stock = stock;
	}
	@Override
	public String toString() {
		return String.format("id=%d, name='%s', description'%s', type=%s, stock=%d",
				id, name, description, type, stock);
	}


	public ConsumableDTO(long id, String name, String description, String type, int stock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.stock = stock;
	}
}