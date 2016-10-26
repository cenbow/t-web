package com.shangpin.biz.bo;

public class ModelOne extends CommonRules {

	private static final long serialVersionUID = 3086432500568692003L;

	private String pic;

	private String height;

	private String width;

	/**
	 * 主标题
	 */
	private String title;
	/**
	 * 副标题
	 */
	private String subTitle;
	/**
	 * 是否已主标题副标题显示 1 显示 0 不显示
	 */
	private String showText;
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	
}
