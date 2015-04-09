package com.glomozda.machinerepair.domain.repairtype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.order.Order;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@Entity
@Table(name = "repair_types")
public class RepairType {
	@Id
	@GeneratedValue
	@Column(name = "repair_types_id")	
	private Long repairTypeId;
	
	@Column(name = "name")
	@NotEmpty
	private String  repairTypeName;
	
	@Column(name = "price")
	@NotNull @DecimalMin("0.0")
	private BigDecimal repairTypePrice;
	
	@Column(name = "duration")
	@NotNull @Min(0)
	private Integer repairTypeDuration;
	
	@OneToMany(mappedBy="repairType")
	private List<Order> orders = new ArrayList<Order>();
	
	public RepairType(){
	}

	public RepairType(final String repairTypeName, final BigDecimal repairTypePrice,
    		final Integer repairTypeDuration) {
        this.repairTypeName = repairTypeName;        
        this.repairTypePrice = repairTypePrice;
        this.repairTypeDuration = repairTypeDuration;
    }
	
	public void addOrder(final Order order) {
        this.orders.add(order);
        if (order.getRepairType() != this) {
            order.setRepairType(this);
        }
    }
	
	public List<Order> getOrders() {
		return orders;
	}

    public String getRepairTypeName() {
        return repairTypeName;
    }

    public void setRepairTypeName(final String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }

    public BigDecimal getRepairTypePrice() {
        return repairTypePrice;
    }

    public void setRepairTypePrice(final BigDecimal repairTypePrice) {
        this.repairTypePrice = repairTypePrice;
    }

    public Long getRepairTypeId() {
        return repairTypeId;
    }

    public void setRepairTypeId(final Long repairTypeId) {
        this.repairTypeId = repairTypeId;
    }    

	public Integer getRepairTypeDuration() {
		return repairTypeDuration;
	}

	public void setRepairTypeDuration(final Integer repairTypeDuration) {
		this.repairTypeDuration = repairTypeDuration;
	}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.repairTypeName != null ? this.repairTypeName.hashCode() : 0);
        hash = 13 * hash + (this.repairTypePrice != null ? this.repairTypePrice.hashCode() : 0);
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
        final RepairType other = (RepairType) obj;
        if ((this.repairTypeName == null) ? other.repairTypeName != null : !this.repairTypeName.equals(other.repairTypeName)) {
            return false;
        }
        if (this.repairTypePrice != other.repairTypePrice && (this.repairTypePrice == null || !this.repairTypePrice.equals(other.repairTypePrice))) {
            return false;
        }
        if (this.repairTypeDuration != other.repairTypeDuration && (this.repairTypeDuration == null || !this.repairTypeDuration.equals(other.repairTypeDuration))) {
            return false;
        }
        return true;
    }    
    
    @Override
    public String toString() {
        return "repairType{" + "repairTypeId=" + repairTypeId + ", repairTypeName=" + repairTypeName + ", repairTypePrice=" + repairTypePrice + ", repairTypeDuration=" + repairTypeDuration + '}'+"\n";
    }
	    
}
