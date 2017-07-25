package org.usfirst.frc.team283.robot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

/**
 * The purpose of this class is to read annotations and generate a schema image based on those annotations
 * @author Benjamin
 */
public class JoystickSchema
{
	public static void main(String[] args)
	{
		JoystickSchema js = new JoystickSchema("org.usfirst.frc.team283.robot.CannonSubsystem", "org.usfirst.frc.team283.robot.DriveSubsystem", "org.usfirst.frc.team283.robot.ElevatorSubsystem");
		js.generate();
		js.loadImage();
	}
	
	//Logitech Ports (Default)
		//Digital
			public static final int A = 0;
			public static final int B = 1;
			public static final int X = 2;
			public static final int Y = 3;
			public static final int LEFT_BUMPER = 4;
			public static final int RIGHT_BUMPER = 5;
			public static final int BACK = 6;
			public static final int START = 7;
			public static final int LEFT_STICK_BUTTON = 8;
			public static final int RIGHT_STICK_BUTTON = 9;
		//Analog
			public static final int LEFT_X = 10;
			public static final int LEFT_Y = 11;
			public static final int LEFT_TRIGGER = 12;
			public static final int RIGHT_TRIGGER = 13;
			public static final int RIGHT_X = 14;
			public static final int RIGHT_Y = 15;
	//Xbox Ports
		//Digital
			public static final int XBOX_A = 16;
			public static final int XBOX_B = 17;
			public static final int XBOX_X = 18;
			public static final int XBOX_Y = 19;
			public static final int XBOX_LEFT_BUMPER = 20;
			public static final int XBOX_RIGHT_BUMPER = 21;
			public static final int XBOX_BACK = 22;
			public static final int XBOX_START = 23;
			public static final int XBOX_LEFT_STICK_BUTTON = 24;
			public static final int XBOX_RIGHT_STICK_BUTTON = 25;
		//Analog
			public static final int XBOX_LEFT_X = 26;
			public static final int XBOX_LEFT_Y = 27;
			public static final int XBOX_LEFT_TRIGGER = 28;
			public static final int XBOX_RIGHT_TRIGGER = 29;
			public static final int XBOX_RIGHT_X = 30;
			public static final int XBOX_RIGHT_Y = 31;	
	//Left Physical Joystick
		//Digital
		//Analog
	//Right Physical Joystick
		//Digital
		//Analog
	
	/** What kind of controller does the driver and operator use? */
	public enum driverMode
	{
		logitech;
	}
	
	@Repeatable(Schemas.class)
	@Retention(RetentionPolicy.RUNTIME)
	/**
	 * Place this annotation in front of "action" functions like so:
	 * (at)Schema(JoystickSchema.BUTTON)
	 * and the JoystickSchema class will read this and put that function with that button on the controls image
	 * See JoystickSchema for a complete list of buttons and axes
	 */
	@interface Schema
	{
		/**
		 * Stores the button/axis that the function is assigned to 
		 * Possible Values: are above
		 */
		int value(); //Since this is named value, you can simply input it next to the annotation
		//String desc(); Possibily consider including this in a later version. Purpose would be to have better naming
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	/**
	 * Container annotation for schemas. Do not use.
	 */
	@interface Schemas
	{
		Schema[] value();
	}
	
	/** Our stored references to all classes in this project. Holds a max of 20 for now */
	Class<?>[] classes = new Class[20];
	
	/**
	 * Takes a list of class names and fetches classes based on that
	 * @param classNames - List of class names
	 */
	JoystickSchema(String... classNames)
	{
		for (int i = 0; i < classNames.length; i++)
		{
			try 
			{
				classes[i] = Class.forName(classNames[i]);
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Takes a list of instantiated classes and stores them
	 * @deprecated
	 * @param classInstances - List of objects of desired classes
	 */
	JoystickSchema(Object... classInstances)
	{
		for (int i = 0; i < classInstances.length; i++)
		{
			classes[i] = classInstances[i].getClass();
		}
	}
	
	/**
	 * Creates an updated control schema/image based on stored class annotations
	 */
	public void generate()
	{
		for (Class<?> c : classes)
		{
			if (c !=null)
			{
				Method[] methods = c.getDeclaredMethods();
		
				for (Method m : methods)
				{
					Schemas allSchemas = m.getAnnotation(Schemas.class);
					if (allSchemas != null)
					{
						for (Schema s : allSchemas.value())
						{
							System.out.println("Function: " + m.getName() + ", Input Number: " + s.value());
						}
					}
				}
			}
		}
		
		
		
	}
	
	/**  */
	public void loadImage()
	{
		BufferedImage img = null;
		try 
		{
		    img = ImageIO.read(new File("controlsSchemaBase.png"));
		    ImageIO.write(img, "png", new File("ControlsImage.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the specified text at the specified index
	 * @param index - Number to place text by
	 * @param text - Text (Description or Function Name)
	 */
	public void addToImage(int index, String text)
	{
		
	}
}
