<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
    <html>
        <head>
        <style>
            table, th, td {
                border: 1px solid black;
            }
        </style>
        </head>
        <body>
            <table>
                <tr>
                    <th>Year</th>
                    <th>Make</th>
                    <th>Model</th>
                    <th>Price</th>
                </tr>

                <xsl:for-each select="cars/car">
                <tr>
                    <td><xsl:value-of select="year"/></td>
                    <td><xsl:value-of select="make"/></td>
                    <td><xsl:value-of select="model"/></td>
                    <td><xsl:value-of select="price"/></td>
                </tr>
                </xsl:for-each>
            </table>
        </body>
    </html>
</xsl:template>
</xsl:stylesheet>