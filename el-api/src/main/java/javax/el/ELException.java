/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */

package javax.el;

/**
 * Represents any of the exception conditions that can arise during
 * expression evaluation.
 *
 * @since JSP 2.1
 */
public class ELException extends RuntimeException {

    //-------------------------------------
    /**
     * Creates an <code>ELException</code> with no detail message.
     */
    public ELException () {
        super ();
    }

    //-------------------------------------
    /**
     * Creates an <code>ELException</code> with the provided detail message.
     *
     * @param pMessage the detail message
     */
    public ELException (String pMessage) {
        super (pMessage);
    }

    //-------------------------------------
    /**
     * Creates an <code>ELException</code> with the given cause.
     *
     * @param pRootCause the originating cause of this exception
     */
    public ELException (Throwable pRootCause) {
        super( pRootCause );
    }

    //-------------------------------------
    /**
     * Creates an ELException with the given detail message and root cause.
     *
     * @param pMessage the detail message
     * @param pRootCause the originating cause of this exception
     */
    public ELException (String pMessage,
                        Throwable pRootCause) {
        super (pMessage, pRootCause);
    }

}