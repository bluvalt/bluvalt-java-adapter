package com.stcs.spa.vo;

public class Unit extends BaseVO {


	private String id,name,short_name,symbol;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}
	/**
	 * @param short_name the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
