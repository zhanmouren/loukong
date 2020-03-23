package com.koron.ebs.permission;

public class SimpleEntity implements EntityID{
	private String id;
	private String name;
	
	public SimpleEntity(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.swan.common.Propertible#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.swan.common.Propertible#setProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void setProperty(String key, String o) {
		
	}

	/* (non-Javadoc)
	 * @see com.koron.ebs.permission.EntityID#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.koron.ebs.permission.EntityID#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
