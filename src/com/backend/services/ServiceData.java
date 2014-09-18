package com.backend.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Service Data
 * 
 * <p>All structured data that is pushed through process boundaries 
 * must extend this class that will wrap it around as a parcelable 
 * implementation. 
 * Use this class as a direct parent of any data models.
 * This class contains Reflection API logic to help marshall and demarshall 
 * the the structured data (as it's subclass) properly on a generic way. 
 * IMPORTANT: Note that only direct subclass is acceptable!
 * <ul> 
 * 	<li>GOOD: public class Direct extends ServiceData { }</li>
 * 	<li>BAD: public class Indirect extends Direct { }</li>
 * </ul>
 * Indirection can be used only if data transfer takes place in the same process 
 * but that is only a special case hence not recommended to be chosen.
 * </p>
 */
public abstract class ServiceData implements Parcelable {
	
	public ServiceData() {
	}
	
	public ServiceData(Parcel in) {
		try {
			rehydrate(this, in);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static final Parcelable.Creator<ServiceData> CREATOR = new Parcelable.Creator<ServiceData>() {
		
		@Override
		public ServiceData createFromParcel(Parcel in) {
			Class<?> parceledClass;
			try {
				parceledClass = Class.forName(in.readString());
				ServiceData model = (ServiceData) parceledClass.newInstance();
				rehydrate(model, in);
				return model;
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(InstantiationException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public ServiceData[] newArray(int size) {
			return new ServiceData[size];
		}
	};
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getClass().getName());
		try {
			dehydrate(this, dest);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	protected static void dehydrate(ServiceData model, Parcel out) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = model.getClass().getDeclaredFields();
		Arrays.sort(fields, compareMemberByName);
		for(Field field : fields) {
			field.setAccessible(true);
			if(field.getType().equals(int.class)) {
				out.writeInt(field.getInt(model));
			} else if(field.getType().equals(double.class)) {
				out.writeDouble(field.getDouble(model));
			} else if(field.getType().equals(float.class)) {
				out.writeFloat(field.getFloat(model));
			} else if(field.getType().equals(long.class)) {
				out.writeLong(field.getLong(model));
			} else if (field.getType().equals(String.class)) {
				out.writeString((String) field.get(model));
			} else if (field.getType().equals(boolean.class)) {
				out.writeByte(field.getBoolean(model) ? (byte) 1 : (byte) 0);
			} else if (field.getType().equals(Date.class)) {
				Date date = (Date) field.get(model);
				if (date != null) {
					out.writeLong(date.getTime());
				} else {
					out.writeLong(0);
				}
			} else if (ServiceData.class.isAssignableFrom(field.getType())) {
				out.writeParcelable((ServiceData) field.get(model), 0);
            } else {
            	// (?)
            }
        }
    }
    
	protected static void rehydrate(ServiceData model, Parcel in) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = model.getClass().getDeclaredFields();
		Arrays.sort(fields, compareMemberByName);
		
		for(Field field : fields){
            field.setAccessible(true);
            if (field.getType().equals(int.class)) {
                        field.set(model, in.readInt());
            } else if (field.getType().equals(double.class)) {
                        field.set(model, in.readDouble());
            } else if (field.getType().equals(float.class)) {
            	field.set(model, in.readFloat());
            } else if (field.getType().equals(long.class)) {
            	field.set(model, in.readLong());
            } else if (field.getType().equals(String.class)) {
                field.set(model, in.readString());
            } else if (field.getType().equals(boolean.class)) {
                field.set(model, in.readByte() == 1);
            } else if (field.getType().equals(Date.class)) {
                        Date date = new Date(in.readLong());
                        field.set(model, date);
            } else if (ServiceData.class.isAssignableFrom(field.getType())) {
                field.set(model, in.readParcelable(field.getType().getClassLoader()));
            } else {
            	// (?)
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	private static Comparator<Field> compareMemberByName = new CompareMemberByName();

    @SuppressWarnings("rawtypes")
	private static class CompareMemberByName implements Comparator {
    	
    	@Override
    	public int compare(Object o1, Object o2) {
    		String s1 = ((Member)o1).getName();
    		String s2 = ((Member)o2).getName();
    		
    		if(o1 instanceof Method) {
    			s1 += getSignature((Method)o1);
    			s2 += getSignature((Method)o2);
    		} else if(o1 instanceof Constructor) {
    			s1 += getSignature((Constructor)o1);
    			s2 += getSignature((Constructor)o2);
    		}
    		
    		return s1.compareTo(s2);
    	}
    }
    
    private static String getSignature(Class<?> clazz) {
    	String type = null;
    	
    	if(clazz.isArray()) {
    		Class<?> cl = clazz;
    		int dimensions = 0;
    		
    		while(cl.isArray()) {
    			dimensions++;
    			cl = cl.getComponentType();
    		}
    		
    		StringBuffer sb = new StringBuffer();
    		
    		for(int i = 0; i < dimensions; i++) {
    			sb.append("[");
    		}
    		
    		sb.append(getSignature(cl));
    		type = sb.toString();
    	} else if(clazz.isPrimitive()) {
    		if(clazz == Integer.TYPE) {
    			type = "I";
    		} else if (clazz == Byte.TYPE) {
    			type = "B";
    		} else if (clazz == Long.TYPE) {
    			type = "J";
    		} else if (clazz == Float.TYPE) {
    			type = "F";
    		} else if (clazz == Double.TYPE) {
    			type = "D";
    		} else if (clazz == Short.TYPE) {
    			type = "S";
    		} else if (clazz == Character.TYPE) {
    			type = "C";
    		} else if (clazz == Boolean.TYPE) {
    			type = "Z";
    		} else if (clazz == Void.TYPE) {
    			type = "V";
    		}
    	} else {
    		type = "L" + clazz.getName().replace('.', '/') + ";";
    	}
    	
    	return type;
    }
    
    private static String getSignature(Method method) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("(");
    	
        @SuppressWarnings("rawtypes")
        Class[] params = method.getParameterTypes();
        
        for(int j = 0; j < params.length; j++) {
        	sb.append(getSignature(params[j]));
        }
        
        sb.append(")");
        sb.append(getSignature(method.getReturnType()));
        
        return sb.toString();
    }
    
    private static String getSignature(Constructor<?> cons) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("(");

    	@SuppressWarnings("rawtypes")
    	Class[] params = cons.getParameterTypes();
    	
    	for(int j = 0; j < params.length; j++) {
    		sb.append(getSignature(params[j]));
    	}
    	
    	sb.append(")V");
    	return sb.toString();
    }
}
