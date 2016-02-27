/*
 * Copyright (C)  Tony Green, LitePal Framework Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eastsoft.android.esbic.util;

import android.util.Log;

public final class LogUtil
{
	private static final String TAG = "esbic";

	public static void print(LogPriorityEnum e, String message)
	{
		switch(e)
		{
			case CORE_LOG_PRI_ERROR: Log.e(TAG, message);break;
			case CORE_LOG_PRI_WARN:	Log.w(TAG, message);break;
			case CORE_LOG_PRI_NOTICE: Log.w(TAG, message);break;
			case CORE_LOG_PRI_INFO: Log.i(TAG, message);break;
			case CORE_LOG_PRI_DEBUG: Log.d(TAG, message);break;
			case CORE_LOG_PRI_TRACE: Log.i(TAG, message);break;
			default: Log.i(TAG, message);
		}
	}

	public static void print(String message)
	{
		Log.i(TAG, message);
	}

	public enum LogPriorityEnum
	{
		CORE_LOG_PRI_ERROR  	(0),
		CORE_LOG_PRI_WARN		(1),
		CORE_LOG_PRI_NOTICE		(2),
		CORE_LOG_PRI_INFO		(3),
		CORE_LOG_PRI_DEBUG		(4),
		CORE_LOG_PRI_TRACE		(5),
		CORE_LOG_PRI_UNKNOWN	(-1),
		;

		int priority;
		LogPriorityEnum(int priority)
		{
			this.priority = priority;
		}

		public static LogPriorityEnum find(int priority)
		{
			for(LogPriorityEnum i : LogPriorityEnum.values())
			{
				if(i.priority == priority)
				{
					return i;
				}
			}
			return CORE_LOG_PRI_UNKNOWN;
		}
	}
}