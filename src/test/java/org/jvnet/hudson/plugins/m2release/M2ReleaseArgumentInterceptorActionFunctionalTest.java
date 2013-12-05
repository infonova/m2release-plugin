/*
 * The MIT License
 *
 * Copyright 2013 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jvnet.hudson.plugins.m2release;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import hudson.util.ArgumentListBuilder;
import hudson.util.Secret;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.JenkinsRule;

/**
 * Would be in {@link M2ReleaseArgumentInterceptorActionTest}, but {@link Secret#fromString} cannot run outside of Jenkins.
 */
public class M2ReleaseArgumentInterceptorActionFunctionalTest {

    @SuppressWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
    @Rule public JenkinsRule r = new JenkinsRule();

    @Test public void password() throws Exception {
        M2ReleaseArgumentInterceptorAction xceptor = new M2ReleaseArgumentInterceptorAction("-Dusername=bob release:prepare release:perform", "s3cr3t");
        ArgumentListBuilder args = xceptor.internalIntercept(new ArgumentListBuilder().add("-B").addTokenized(xceptor.getGoalsAndOptions(null)), false);
        assertEquals("[-B, -Dusername=bob, release:prepare, release:perform, -Dpassword=s3cr3t]", Arrays.toString(args.toCommandArray()));
        assertEquals("-B -Dusername=bob release:prepare release:perform ******", args.toString());
    }

}
