/*
 * Copyright (C) 2011 The Android Open Source Project
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

package barqsoft.footballscores.widget;

public class WidgetItem {
    public String homeTeam;
    public String awayTeam;
    public String matchDay;
    public String time;
    public String homeGoals;
    public String awayGoals;

    public WidgetItem(String homeTeam,
                      String awayTeam,
                      String matchDay,
                      String time,
                      String homeGoals,
                      String awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDay = matchDay;
        this.time = time;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }
}
