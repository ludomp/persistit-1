/**
 * Copyright © 2012 Akiban Technologies, Inc.  All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, version 3 (only) of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * This program may also be available under different license terms. For more
 * information, see www.akiban.com or contact licensing@akiban.com.
 */

package com.persistit.mxbeans;

import javax.management.MXBean;

/**
 * Management information about the CLEANUP_MANAGER, a thread that performs
 * background pruning and other cleanup tasks.
 */
@MXBean
public interface CheckpointManagerMXBean {

    public final static String MXBEAN_NAME = "com.persistit:type=Persistit,class=CheckpointManager";

    @Description("Checkpoint most recently proposed")
    String getProposedCheckpoint();

    @Description("Checkpoint polling interval in seconds")
    long getCheckpointInterval();

    @Description("Checkpoint polling interval in seconds")
    void setCheckpointInterval(long interval);

    @Description("Number of proposed checkpoints that have not been confirmed")
    int getOutstandingCheckpointCount();

    @Description("Report of proposed checkpoints that have not been confirmed")
    String outstandingCheckpointReport();

}