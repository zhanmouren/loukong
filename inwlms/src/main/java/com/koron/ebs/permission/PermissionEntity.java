package com.koron.ebs.permission;

/**
 * 在权限判断时用来存进行验证的相关信息
 * 
 * @author swan
 *
 */
public class PermissionEntity {
	private Account account;
	private Group group;
	private Role role;
	private Operation operation;
	private Rule rule;

	public PermissionEntity() {
		super();
	}

	public PermissionEntity(Account account, Group group, Role role, Operation operation, Rule rule) {
		super();
		this.account = account;
		this.group = group;
		this.role = role;
		this.operation = operation;
		this.rule = rule;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("account:").append(account.getId()).append("[").append(account.getName()).append("]");
		if (group != null)
			sb.append("group:").append(group.getId()).append("[").append(group.getName()).append("]");
		if (role != null)
			sb.append("role:").append(role.getId()).append("[").append(role.getName()).append("]");
		if (operation != null)
			sb.append("operation:").append(operation.getId()).append("[").append(operation.getName()).append("]");
		if (rule != null)
			sb.append("rule:").append(rule.getId()).append("[").append(rule.getName()).append("]");
		return sb.toString();
	}
}
