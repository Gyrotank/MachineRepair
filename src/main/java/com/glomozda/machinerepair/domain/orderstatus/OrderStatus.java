package com.glomozda.machinerepair.domain.orderstatus;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.order.Order;

@NamedQueries({
	@NamedQuery(name="OrderStatus.findAll", 
		query="SELECT os FROM OrderStatus os ORDER BY os.orderStatusNumber"),
	@NamedQuery(name="OrderStatus.findOrderStatusByName", 
		query="SELECT os FROM OrderStatus os"
			+ " WHERE os.orderStatusName = :name")
})
@Entity
@Table(name = "order_statuses")
public class OrderStatus {
	
	@Id
	@GeneratedValue
	@Column(name = "order_statuses_id")
	private Long orderStatusId;
	
	@Column(name = "order_status_number")
	@NotNull @Min(1)
	private Integer orderStatusNumber;
	
	@Column(name = "order_status_name")
	@NotEmpty
	private String orderStatusName;
	
	@Column(name = "order_status_name_ru")
	@NotEmpty
	private String orderStatusNameRu;
	
	@OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
	private List<Order> orders = new ArrayList<Order>();
	
	public OrderStatus() {		
	}
	
	public OrderStatus(final Integer orderStatusNumber, final String orderStatusName,
			final String orderStatusNameRu) {
		this.orderStatusNumber = orderStatusNumber;
		this.orderStatusName = orderStatusName;
		this.orderStatusNameRu = orderStatusNameRu;
	}

	public Long getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Integer getOrderStatusNumber() {
		return orderStatusNumber;
	}

	public void setOrderStatusNumber(Integer orderStatusNumber) {
		this.orderStatusNumber = orderStatusNumber;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderStatusNameRu() {
		return orderStatusNameRu;
	}

	public void setOrderStatusNameRu(String orderStatusNameRu) {
		this.orderStatusNameRu = orderStatusNameRu;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(final Order order) {
        this.orders.add(order);
        if (order.getStatus() != this) {
            order.setStatus(this);
        }
    }
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.orderStatusNumber != null ? this.orderStatusNumber.hashCode() : 0);
		hash = 13 * hash + (this.orderStatusName != null ? this.orderStatusName.hashCode() : 0);
		hash = 13 * hash + (this.orderStatusNameRu != null ? this.orderStatusNameRu.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OrderStatus other = (OrderStatus) obj;
		if ((this.orderStatusNumber == null) ? other.orderStatusNumber != null 
				: !this.orderStatusNumber.equals(other.orderStatusNumber)) {
			return false;
		}		
		if ((this.orderStatusName == null) ? other.orderStatusName != null 
				: !this.orderStatusName.equals(other.orderStatusName)) {
			return false;
		}
		if ((this.orderStatusNameRu == null) ? other.orderStatusNameRu != null 
				: !this.orderStatusNameRu.equals(other.orderStatusNameRu)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderStatus [orderStatusId=");
		builder.append(orderStatusId);
		builder.append(", orderStatusNumber=");
		builder.append(orderStatusNumber);
		builder.append(", orderStatusName=");
		builder.append(orderStatusName);
		builder.append(", orderStatusNameRu=");
		builder.append(orderStatusNameRu);
		builder.append(", orders=");
		builder.append(orders);
		builder.append("]");
		return builder.toString();
	}
}
