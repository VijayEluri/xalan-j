/*
 * @(#)$Id$
 *
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2002-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xalan" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2001, Sun
 * Microsystems., http://www.sun.com.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * @author Myriam Midy
 *
 */
package org.apache.xpath.axes;

import org.apache.xml.dtm.DTM;
import org.apache.xpath.XPathContext;

/**
 * <meta name="usage" content="advanced"/>
 * This class implements an optimized iterator for
 * "." patterns, that is, the self axis without any predicates.  
 * @see org.apache.xpath.axes.WalkerFactory#newLocPathIterator
 */
public class SingletonIterator extends LocPathIterator {
    /**
     * Create a SingletonIterator object.
     *
     * @param compiler A reference to the Compiler that contains the op map.
     * @param opPos The position within the op map, which contains the
     * location path expression for this itterator.
     * @param analysis Analysis bits.
     *
     * @throws javax.xml.transform.TransformerException
     */
    public SingletonIterator(int node)
          throws javax.xml.transform.TransformerException {
        super(null);
        m_node = node;
    }
  
    private int m_node;


    /**
     *  Returns the next node in the set and advances the position of the
     * iterator in the set. After a NodeIterator is created, the first call
     * to nextNode() returns the first node in the set.
     *
     * @return  The next <code>Node</code> in the set being iterated over, or
     *   <code>null</code> if there are no more members in that set.
     */
    public int nextNode() {
      if (m_foundLast) {
          m_lastFetched = DTM.NULL;
          return DTM.NULL;
      }

      final int result = m_node;
      m_lastFetched = m_node;
      m_node = DTM.NULL;
      m_foundLast = true;
      return result;    
    }
  
    /**
     * Return the first node out of the nodeset, if this expression is 
     * a nodeset expression.  This is the default implementation for 
     * nodesets.  Derived classes should try and override this and return a 
     * value without having to do a clone operation.
     * @param xctxt The XPath runtime context.
     * @return the first node out of the nodeset, or DTM.NULL.
     */
    public int asNode(XPathContext xctxt)
      throws javax.xml.transform.TransformerException {
        return m_node;
    }
  
    /**
     * Get the index of the last node that can be itterated to.
     * This probably will need to be overridded by derived classes.
     *
     * @param xctxt XPath runtime context.
     *
     * @return the index of the last node that can be itterated to.
     */
    public int getLastPos(XPathContext xctxt) {
        return 1;
    }
}