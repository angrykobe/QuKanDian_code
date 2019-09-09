package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class IncomeDetialsBean {
    /**
     * todaySumGold : 32
     * todaySumCoin : 0
     * coinAccount : {"amount":252.925,"sum":252.925,"preAmount":0,"id":10053}
     * goldAccount : {"amount":32,"sum":1457,"id":10053}
     * g2cLog : {"userId":10053,"goldAmount":108,"coinAmount":0.012,"shiftGoldAmount":108,"shiftCoinAmount":0.108,"gcDay":{"totalGold":4051,"shiftGold":3872,"payCoin":0,"newCoin":3.872,"rate":100,"rawRate":100},"lastG2C":{"userId":10053,"goldAmount":12,"coinAmount":0,"shiftGoldAmount":12,"shiftCoinAmount":0.012,"gcDay":{"totalGold":2040,"shiftGold":1851,"payCoin":0,"newCoin":1.851,"rate":100,"rawRate":100}}}
     * id : 10053
     */

    private int todaySumGold = 0;
    private int todaySumCoin = 0;
    private CoinAccountBean coinAccount;
    private GoldAccountBean goldAccount;
    private G2cLogBean g2cLog;
    private int id = 0;

    public int getTodaySumGold() {
        return todaySumGold;
    }

    public void setTodaySumGold(int todaySumGold) {
        this.todaySumGold = todaySumGold;
    }

    public int getTodaySumCoin() {
        return todaySumCoin;
    }

    public void setTodaySumCoin(int todaySumCoin) {
        this.todaySumCoin = todaySumCoin;
    }

    public CoinAccountBean getCoinAccount() {
        return coinAccount;
    }

    public void setCoinAccount(CoinAccountBean coinAccount) {
        this.coinAccount = coinAccount;
    }

    public GoldAccountBean getGoldAccount() {
        return goldAccount;
    }

    public void setGoldAccount(GoldAccountBean goldAccount) {
        this.goldAccount = goldAccount;
    }

    public G2cLogBean getG2cLog() {
        return g2cLog;
    }

    public void setG2cLog(G2cLogBean g2cLog) {
        this.g2cLog = g2cLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class CoinAccountBean {
        /**
         * amount : 252.925
         * sum : 252.925
         * preAmount : 0
         * id : 10053
         */

        private double amount = 0;
        private double sum = 0;
        private int preAmount = 0;
        private int id = 0;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public int getPreAmount() {
            return preAmount;
        }

        public void setPreAmount(int preAmount) {
            this.preAmount = preAmount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class GoldAccountBean {
        /**
         * amount : 32
         * sum : 1457
         * id : 10053
         */

        private int amount = 0;
        private int sum = 0;
        private int id = 0;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class G2cLogBean {
        /**
         * userId : 10053
         * goldAmount : 108
         * coinAmount : 0.012
         * shiftGoldAmount : 108
         * shiftCoinAmount : 0.108
         * dailyUserRateGlobal
         * gcDay : {"totalGold":4051,"shiftGold":3872,"payCoin":0,"newCoin":3.872,"rate":100,"rawRate":100}
         * lastG2C : {"userId":10053,"goldAmount":12,"coinAmount":0,"shiftGoldAmount":12,"shiftCoinAmount":0.012,"gcDay":{"totalGold":2040,"shiftGold":1851,"payCoin":0,"newCoin":1.851,"rate":100,"rawRate":100}}
         */

        private int userId = 0;
        private int goldAmount = 0;
        private double coinAmount = 0;
        private int shiftGoldAmount = 0;
        private double shiftCoinAmount = 0;
        private double dailyUserRateGlobal = 0;
        private GcDayBean gcDay;
        private LastG2CBean lastG2C;

        public double getDailyUserRateGlobal() {
            return dailyUserRateGlobal;
        }

        public void setDailyUserRateGlobal(double dailyUserRateGlobal) {
            this.dailyUserRateGlobal = dailyUserRateGlobal;
        }

        public int getDailyUserRate() {
            return dailyUserRate;
        }

        public void setDailyUserRate(int dailyUserRate) {
            this.dailyUserRate = dailyUserRate;
        }

        private int dailyUserRate;


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGoldAmount() {
            return goldAmount;
        }

        public void setGoldAmount(int goldAmount) {
            this.goldAmount = goldAmount;
        }

        public double getCoinAmount() {
            return coinAmount;
        }

        public void setCoinAmount(double coinAmount) {
            this.coinAmount = coinAmount;
        }

        public int getShiftGoldAmount() {
            return shiftGoldAmount;
        }

        public void setShiftGoldAmount(int shiftGoldAmount) {
            this.shiftGoldAmount = shiftGoldAmount;
        }

        public double getShiftCoinAmount() {
            return shiftCoinAmount;
        }

        public void setShiftCoinAmount(double shiftCoinAmount) {
            this.shiftCoinAmount = shiftCoinAmount;
        }

        public GcDayBean getGcDay() {
            return gcDay;
        }

        public void setGcDay(GcDayBean gcDay) {
            this.gcDay = gcDay;
        }

        public LastG2CBean getLastG2C() {
            return lastG2C;
        }

        public void setLastG2C(LastG2CBean lastG2C) {
            this.lastG2C = lastG2C;
        }

        public class GcDayBean {
            /**
             * totalGold : 4051
             * shiftGold : 3872
             * payCoin : 0
             * newCoin : 3.872
             * rate : 100
             * rawRate : 100
             */

            private int totalGold = 0;
            private int shiftGold = 0;
            private int payCoin = 0;
            private double newCoin = 0;
            private int rate = 0;
            private int rawRate = 0;

            public int getTotalGold() {
                return totalGold;
            }

            public void setTotalGold(int totalGold) {
                this.totalGold = totalGold;
            }

            public int getShiftGold() {
                return shiftGold;
            }

            public void setShiftGold(int shiftGold) {
                this.shiftGold = shiftGold;
            }

            public int getPayCoin() {
                return payCoin;
            }

            public void setPayCoin(int payCoin) {
                this.payCoin = payCoin;
            }

            public double getNewCoin() {
                return newCoin;
            }

            public void setNewCoin(double newCoin) {
                this.newCoin = newCoin;
            }

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }

            public int getRawRate() {
                return rawRate;
            }

            public void setRawRate(int rawRate) {
                this.rawRate = rawRate;
            }
        }

        public class LastG2CBean {
            /**
             * userId : 10053
             * goldAmount : 12
             * coinAmount : 0
             * shiftGoldAmount : 12
             * shiftCoinAmount : 0.012
             * gcDay : {"totalGold":2040,"shiftGold":1851,"payCoin":0,"newCoin":1.851,"rate":100,"rawRate":100}
             */

            private int userId = 0;
            private int goldAmount = 0;
            private double coinAmount = 0;
            private int shiftGoldAmount = 0;
            private double shiftCoinAmount = 0;
            private GcDayBeanX gcDay;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getGoldAmount() {
                return goldAmount;
            }

            public void setGoldAmount(int goldAmount) {
                this.goldAmount = goldAmount;
            }

            public double getCoinAmount() {
                return coinAmount;
            }

            public void setCoinAmount(double coinAmount) {
                this.coinAmount = coinAmount;
            }

            public int getShiftGoldAmount() {
                return shiftGoldAmount;
            }

            public void setShiftGoldAmount(int shiftGoldAmount) {
                this.shiftGoldAmount = shiftGoldAmount;
            }

            public double getShiftCoinAmount() {
                return shiftCoinAmount;
            }

            public void setShiftCoinAmount(double shiftCoinAmount) {
                this.shiftCoinAmount = shiftCoinAmount;
            }

            public GcDayBeanX getGcDay() {
                return gcDay;
            }

            public void setGcDay(GcDayBeanX gcDay) {
                this.gcDay = gcDay;
            }

            public class GcDayBeanX {
                /**
                 * totalGold : 2040
                 * shiftGold : 1851
                 * payCoin : 0
                 * newCoin : 1.851
                 * rate : 100
                 * rawRate : 100
                 */

                private int totalGold = 0;
                private int shiftGold = 0;
                private int payCoin = 0;
                private double newCoin = 0;
                private int rate = 0;
                private int rawRate = 0;

                public int getTotalGold() {
                    return totalGold;
                }

                public void setTotalGold(int totalGold) {
                    this.totalGold = totalGold;
                }

                public int getShiftGold() {
                    return shiftGold;
                }

                public void setShiftGold(int shiftGold) {
                    this.shiftGold = shiftGold;
                }

                public int getPayCoin() {
                    return payCoin;
                }

                public void setPayCoin(int payCoin) {
                    this.payCoin = payCoin;
                }

                public double getNewCoin() {
                    return newCoin;
                }

                public void setNewCoin(double newCoin) {
                    this.newCoin = newCoin;
                }

                public int getRate() {
                    return rate;
                }

                public void setRate(int rate) {
                    this.rate = rate;
                }

                public int getRawRate() {
                    return rawRate;
                }

                public void setRawRate(int rawRate) {
                    this.rawRate = rawRate;
                }
            }
        }
    }
}
