/*
 * @(#)BaseObject.java $version 2013. 7. 23.
 *
 * Copyright 2013 NHN Entertainment Corp. All rights Reserved. 
 * NHN Entertainment PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.dasoulte.simons.core.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseObject implements Serializable {
	private static final long serialVersionUID = 4926409657799354663L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
