package com.wyiwei.commons.hashcode_equals;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 复写equals方法必须复写hashCode方法
 * @author hasee
 *
 */
public class HashCodeBean {
		
		public long start;
		public long end;
		
		public HashCodeBean() {}
		
		public HashCodeBean(long start, long end) {
			super();
			this.start = start;
			this.end = end;
		}

		/**
		 * 拿来主义
		 */
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
		
//		@Override
//		public String toString() {
//			return new StringBuilder().append(String.format("%s.start=%s", this.getClass(), start)).append(", ")
//					.append(String.format("%s.end=%s", this.getClass(), end)).toString();
//		}
		
//		@Override
//		public int hashCode(){
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + (int) (end ^ (end >>> 32));
//			result = prime * result + (int) (start ^ (start >>> 32));
//			return result;
//		}
		
		/**
		 * 可以用拿来主义
		 */
		@Override
		public int hashCode(){
			return new HashCodeBuilder().append(start).append(end).toHashCode();
		}
		
		@Override
		public boolean equals(Object obj){
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			HashCodeBean other = (HashCodeBean) obj;
			if(end != other.end)
				return false;
			if(start != other.start)
				return false;
			return true;
		}
		
	}