/**
 * Copyright (c) 2015-2016 Peti Koch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.petikoch.examples.mvvm_rxjava.example2;

import java.util.concurrent.atomic.AtomicInteger;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class Example_2_Model {

	private final AtomicInteger failToken = new AtomicInteger(1);
    
	public void createAccount(NameFirstname nameFirstname) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
    	if ( failToken.incrementAndGet() % 3 == 0 )
    		throw new RuntimeException( "oops" );
    	else
    		SysOutUtils.sysout("Model: " + nameFirstname.toString());
    }
}
