<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet
        xmlns="http://dtd.riege.com/daddel/daddel"
        xmlns:da="http://dtd.riege.com/daddel/daddel"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        exclude-result-prefixes="da"
        version="1.0">

    <xsl:output method="xml" encoding="utf-8" />
    
    <xsl:template match="text()" />

    <xsl:template match="da:foo">
        <xsl:copy />
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="da:foo/node()[not(text())]">
        <xsl:text>
            <xsl:value-of select="position()"/>
        </xsl:text>
    </xsl:template>

</xsl:stylesheet>