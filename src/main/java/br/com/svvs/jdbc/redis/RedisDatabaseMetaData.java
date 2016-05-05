package br.com.svvs.jdbc.redis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public class RedisDatabaseMetaData implements DatabaseMetaData {

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor");
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        throw new SQLFeatureNotSupportedException("allProceduresAreCallable");
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        throw new SQLFeatureNotSupportedException("allTablesAreSelectable");
    }

    @Override
    public String getURL() throws SQLException {
        throw new SQLFeatureNotSupportedException("getURL");
    }

    @Override
    public String getUserName() throws SQLException {
        throw new SQLFeatureNotSupportedException("getUserName");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new SQLFeatureNotSupportedException("isReadOnly");
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        throw new SQLFeatureNotSupportedException("nullsAreSortedHigh");
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        throw new SQLFeatureNotSupportedException("nullsAreSortedLow");
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        throw new SQLFeatureNotSupportedException("nullsAreSortedAtStart");
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        throw new SQLFeatureNotSupportedException("nullsAreSortedAtEnd");
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDatabaseProductName");
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDatabaseProductVersion");
    }

    @Override
    public String getDriverName() throws SQLException {
        return "Redis JDBC Driver";
    }

    @Override
    public String getDriverVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDriverVersion");
    }

    @Override
    public int getDriverMajorVersion() {
        return 0;
    }

    @Override
    public int getDriverMinorVersion() {
        return 1;
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        throw new SQLFeatureNotSupportedException("usesLocalFiles");
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        throw new SQLFeatureNotSupportedException("usesLocalFilePerTable");
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMixedCaseIdentifiers");
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesUpperCaseIdentifiers");
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesLowerCaseIdentifiers");
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesMixedCaseIdentifiers");
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMixedCaseQuotedIdentifiers");
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesUpperCaseQuotedIdentifiers");
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesLowerCaseQuotedIdentifiers");
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        throw new SQLFeatureNotSupportedException("storesMixedCaseQuotedIdentifiers");
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        throw new SQLFeatureNotSupportedException("getIdentifierQuoteString");
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLKeywords");
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        throw new SQLFeatureNotSupportedException("getNumericFunctions");
    }

    @Override
    public String getStringFunctions() throws SQLException {
        throw new SQLFeatureNotSupportedException("getStringFunctions");
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSystemFunctions");
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimeDateFunctions");
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSearchStringEscape");
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        throw new SQLFeatureNotSupportedException("getExtraNameCharacters");
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsAlterTableWithAddColumn");
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsAlterTableWithDropColumn");
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsColumnAliasing");
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        throw new SQLFeatureNotSupportedException("nullPlusNonNullIsNull");
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsConvert");
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsConvert");
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsTableCorrelationNames");
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsDifferentTableCorrelationNames");
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsExpressionsInOrderBy");
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOrderByUnrelated");
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsGroupBy");
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsGroupByUnrelated");
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsGroupByBeyondSelect");
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsLikeEscapeClause");
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMultipleResultSets");
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMultipleTransactions");
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsNonNullableColumns");
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMinimumSQLGrammar");
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCoreSQLGrammar");
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsExtendedSQLGrammar");
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsANSI92EntryLevelSQL");
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsANSI92IntermediateSQL");
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsANSI92FullSQL");
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsIntegrityEnhancementFacility");
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOuterJoins");
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsFullOuterJoins");
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsLimitedOuterJoins");
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSchemaTerm");
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        throw new SQLFeatureNotSupportedException("getProcedureTerm");
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        throw new SQLFeatureNotSupportedException("getCatalogTerm");
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        throw new SQLFeatureNotSupportedException("isCatalogAtStart");
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        throw new SQLFeatureNotSupportedException("getCatalogSeparator");
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSchemasInDataManipulation");
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSchemasInProcedureCalls");
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSchemasInTableDefinitions");
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSchemasInIndexDefinitions");
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSchemasInPrivilegeDefinitions");
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCatalogsInDataManipulation");
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCatalogsInProcedureCalls");
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCatalogsInTableDefinitions");
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCatalogsInIndexDefinitions");
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCatalogsInPrivilegeDefinitions");
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsPositionedDelete");
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsPositionedUpdate");
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSelectForUpdate");
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsStoredProcedures");
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSubqueriesInComparisons");
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSubqueriesInExists");
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSubqueriesInIns");
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSubqueriesInQuantifieds");
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsCorrelatedSubqueries");
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsUnion");
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsUnionAll");
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOpenCursorsAcrossCommit");
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOpenCursorsAcrossRollback");
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOpenStatementsAcrossCommit");
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsOpenStatementsAcrossRollback");
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxBinaryLiteralLength");
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxCharLiteralLength");
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnNameLength");
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnsInGroupBy");
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnsInIndex");
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnsInOrderBy");
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnsInSelect");
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxColumnsInTable");
    }

    @Override
    public int getMaxConnections() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxConnections");
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxCursorNameLength");
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxIndexLength");
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxSchemaNameLength");
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxProcedureNameLength");
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxCatalogNameLength");
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxRowSize");
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        throw new SQLFeatureNotSupportedException("doesMaxRowSizeIncludeBlobs");
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxStatementLength");
    }

    @Override
    public int getMaxStatements() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxStatements");
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxTableNameLength");
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxTablesInSelect");
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMaxUserNameLength");
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDefaultTransactionIsolation");
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsTransactions");
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsTransactionIsolationLevel");
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsDataDefinitionAndDataManipulationTransactions");
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsDataManipulationTransactionsOnly");
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        throw new SQLFeatureNotSupportedException("dataDefinitionCausesTransactionCommit");
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        throw new SQLFeatureNotSupportedException("dataDefinitionIgnoredInTransactions");
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getProcedures");
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern,
            String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getProcedureColumns");
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTables");
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSchemas");
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        throw new SQLFeatureNotSupportedException("getCatalogs");
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        throw new SQLFeatureNotSupportedException("getTableTypes");
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getColumns");
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getColumnPrivileges");
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTablePrivileges");
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getBestRowIdentifier");
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException("getVersionColumns");
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException("getPrimaryKeys");
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException("getImportedKeys");
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException("getExportedKeys");
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable,
            String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCrossReference");
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        throw new SQLFeatureNotSupportedException("getTypeInfo");
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getIndexInfo");
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsResultSetType");
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsResultSetConcurrency");
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("ownUpdatesAreVisible");
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("ownDeletesAreVisible");
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("ownInsertsAreVisible");
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("othersUpdatesAreVisible");
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("othersDeletesAreVisible");
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("othersInsertsAreVisible");
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("updatesAreDetected");
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("deletesAreDetected");
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException("insertsAreDetected");
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsBatchUpdates");
    }

    @Override
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getUDTs");
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new SQLFeatureNotSupportedException("getConnection");
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsSavepoints");
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsNamedParameters");
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsMultipleOpenResults");
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsGetGeneratedKeys");
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSuperTypes");
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSuperTables");
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern,
            String attributeNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAttributes");
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsResultSetHoldability");
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getResultSetHoldability");
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDatabaseMajorVersion");
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getDatabaseMinorVersion");
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getJDBCMajorVersion");
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        throw new SQLFeatureNotSupportedException("getJDBCMinorVersion");
    }

    @Override
    public int getSQLStateType() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLStateType");
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        throw new SQLFeatureNotSupportedException("locatorsUpdateCopy");
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsStatementPooling");
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        throw new SQLFeatureNotSupportedException("getRowIdLifetime");
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSchemas");
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        throw new SQLFeatureNotSupportedException("supportsStoredFunctionsUsingCallSyntax");
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        throw new SQLFeatureNotSupportedException("autoCommitFailureClosesAllResultSets");
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        throw new SQLFeatureNotSupportedException("getClientInfoProperties");
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getFunctions");
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern,
            String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getFunctionColumns");
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern,
            String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException("getPseudoColumns");
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new SQLFeatureNotSupportedException("generatedKeyAlwaysReturned");
    }


}
