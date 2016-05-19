package com.stcs.spa.vo;

import java.util.Date;

public class Item extends BaseVO {

	private int id;
	private String meta_data,name,description;
	private Date created,modified;
	private Unit[] units; 
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the meta_data
	 */
	public String getMeta_data() {
		return meta_data;
	}
	/**
	 * @param meta_data the meta_data to set
	 */
	public void setMeta_data(String meta_data) {
		this.meta_data = meta_data;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
	 * @return the units
	 */
	public Unit[] getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(Unit[] units) {
		this.units = units;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
