/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2020 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.mysql.model;

import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.model.DBPDataSource;
import org.jkiss.dbeaver.model.DBPEvaluationContext;
import org.jkiss.dbeaver.model.DBUtils;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCResultSet;
import org.jkiss.dbeaver.model.impl.jdbc.JDBCUtils;
import org.jkiss.dbeaver.model.meta.Property;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.struct.DBSEntityConstraintType;

import java.util.List;

public class MySQLTableCheckConstraint extends MySQLTableConstraintBase {
    private List<MySQLTableConstraintColumn> columns;
    private String clause;

    public MySQLTableCheckConstraint(MySQLTable table, String name, String description, DBSEntityConstraintType constraintType, boolean persisted, JDBCResultSet resultSet) {
        super(table, name, description, constraintType, persisted, resultSet);
        this.clause = JDBCUtils.safeGetString(resultSet, "CHECK_CLAUSE");
    }

    public MySQLTableCheckConstraint(MySQLTable table, String name, String description, DBSEntityConstraintType constraintType, boolean persisted) {
        super(table, name, description, constraintType, persisted);
    }

    @Override
    public List<MySQLTableConstraintColumn> getAttributeReferences(DBRProgressMonitor monitor) throws DBException {
        return columns;
    }

    @Property(viewable = true, editable = true, order = 2)
    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    @Override
    public String getFullyQualifiedName(DBPEvaluationContext context) {
        return DBUtils.getFullQualifiedName(getDataSource(),
                getTable().getContainer(),
                getTable(),
                this);
    }

    @Override
    public DBPDataSource getDataSource() {
        return getTable().getDataSource();
    }
}
