<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="iso-8859-1"/>
<xsl:template match="/Domain">
/*******
 MACHINE GENERATED, DO NOT EDIT.
 Generated by gatech.mmpm.tools.IDomainGenerator, written by Marco Antonio G�mez Mart�n
 *******/
package <xsl:value-of select="attribute::package"/>;


import gatech.mmpm.sensor.Sensor;

<!--
<xsl:if test="ActionSet[@package]">
import <xsl:value-of select="ActionSet/attribute::package"/>.*;</xsl:if>
<xsl:if test="ConditionSet[@package]">
import <xsl:value-of select="ConditionSet/attribute::package"/>.*;</xsl:if>
<xsl:if test="SensorSet[@package]">
import <xsl:value-of select="SensorSet/attribute::package"/>.*;</xsl:if>
<xsl:if test="GoalSet[@package]">
import <xsl:value-of select="GoalSet/attribute::package"/>.*;</xsl:if>
<xsl:if test="EntitySet[@package]">
import <xsl:value-of select="EntitySet/attribute::package"/>.*;</xsl:if>
-->
  
/**
 Class for the '<xsl:value-of select="attribute::name"/>' domain.

 This class is machine-generated. If you have to change anything,
 probably you want to edit the XML file used to create this class.
 @author gatech.mmpm.tools.DomainGenerator, developed by Marco Antonio G�mez Mart�n
 and David Llanso.
*/
public class <xsl:value-of select="attribute::classname"/> implements gatech.mmpm.IDomain {

    public String getName() {
       return getDomainName();
    }

	/* *
	 * Return the world model of the game.
	 * @return World model or null if there is no one.
	 */
/*
    // This was to be a D2 hack. MMPM will not provide that.
    public worldmodel.WorldModel getWorldModel() {
       <xsl:choose>
       <xsl:when test="./WorldModel/Name">
       return new <xsl:value-of select="./WorldModel/Name"/>();</xsl:when>
       <xsl:otherwise>
       // No specific WorldModel in '<xsl:value-of select="attribute::name"/>' domain
       // Default used.
       return new worldmodel.WorldModel();</xsl:otherwise>
       </xsl:choose>
    }
*/

    /**
     * Method that defines all the entities in the domain.
     * @return An array with all the Class'es that implement the
     * D2 entities for the domain.
    */
    public Class&lt;? extends gatech.mmpm.Entity&gt;[] getEntities() {

        Class&lt;? extends gatech.mmpm.Entity&gt; []ret;

        ret = new Class[] {
                    <xsl:for-each select="./EntitySet/Entity/Name">
                      <xsl:value-of select="../../attribute::package"/>.<xsl:value-of select="."/>.class,
                    </xsl:for-each>
              };

        return ret;        
    }

    /**
    * Method that return a new entity generated from its short name class.
    * @return New instance of the specified entity.
    */
    public gatech.mmpm.Entity getEntityByShortName(char shortName, String entityId, String owner) {

        gatech.mmpm.Entity ret;

        switch (shortName) {
        <xsl:for-each select="./EntitySet/Entity/Name">
          <xsl:choose>
            <xsl:when test="../ShortName">
            case '<xsl:value-of select="../ShortName"/>':
                ret = new <xsl:value-of select="../../attribute::package"/>.<xsl:value-of select="../Name"/>(entityId,owner);
            break;
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
      default: ret = null;
      };

      return ret;
      }

      /**
      * Method that return a new entity generated from its name class.
      * @return New instance of the specified entity.
      */
      public gatech.mmpm.Entity getEntityByName(String name, String entityId, String owner) {
      <xsl:for-each select="./EntitySet/Entity/Name">
        if( name.equals("<xsl:value-of select="."/>") )
        return new <xsl:value-of select="../../attribute::package"/>.<xsl:value-of select="."/>(entityId,owner);
      </xsl:for-each>
        return null;
    }

    /**
     Method that defines all the actions in the domain.
     @return An array with all the Class'es that implement the
     D2 actions for the domain.
    */
    public Class[] getActions() {
        Class []ret;

        ret = new Class[] {
                    <xsl:for-each select="./ActionSet/Action">
                      <xsl:value-of select="../attribute::package"/>.<xsl:value-of select="@name"/>.class,
                    </xsl:for-each>
              };

        return ret;        
    }

    /**
     Method that defines all the sensors in the domain.
     @return An array with all sensors.
    */
    public Sensor[] getSensors() {
        Sensor []ret;

        ret = new Sensor[] {
                     <xsl:for-each select="./SensorSet/Sensor">
                        new <xsl:value-of select="../attribute::package"/>.<xsl:value-of select="@name"/>(),
                     </xsl:for-each>
              };

        return ret;        
    }

    /**
     Method that defines all the goals in the domain.
     @return An array with all goals.
    */
    public Sensor[] getGoals() {
        Sensor []ret;

        ret = new Sensor[] {
           <xsl:for-each select="./GoalSet/Goal">
                        new <xsl:value-of select="../attribute::package"/>.<xsl:value-of select="@name"/>(),
           </xsl:for-each>
           <xsl:choose>
             <xsl:when test="./GoalSet/WinGoal">
                        new <xsl:value-of select="./GoalSet/attribute::package"/>.<xsl:value-of select="./GoalSet/WinGoal/@name"/>()
             </xsl:when>
           </xsl:choose>
              };

        return ret;        
    }

    /**
     Method that return the win goal condition.

     @return An object (Sensor) that represent the win goal.
    */
    public Sensor getWinGoal() {
       <xsl:choose>
       <xsl:when test="./GoalSet/WinGoal">
       return new <xsl:value-of select="./GoalSet/attribute::package"/>.<xsl:value-of select="./GoalSet/WinGoal/@name"/>();</xsl:when>
       <xsl:otherwise>
       // No WinGoal in '<xsl:value-of select="attribute::name"/>' domain
       return null;</xsl:otherwise>
       </xsl:choose>
    }

    // This static method could be used in the game
    // itself; it is not forced by the gatech.mmpm.IDomain interface
    public static String getDomainName() {
        return "<xsl:value-of select="attribute::name"/>";
    }

	// Someone included this method in gatech.mmpm.domain
	// and did not
	// update the domain generation tool.
	// A hack has been done: the generation tool
	// implements this method returning always null.
	// If this is important, then the XML with the
	// game domain should include the value to be
	// returned here, and the tool should be updated.
	public String getRulesFile() {
		return null;
	}
}
</xsl:template>
</xsl:stylesheet>