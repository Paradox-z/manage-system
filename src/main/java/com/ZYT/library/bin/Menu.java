package com.ZYT.library.bin;

import com.ZYT.library.dao.*;
import com.ZYT.library.entity.*;

import javax.crypto.spec.PSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Menu {
    /*
     *
     * stepped rent
     * 7 1 1
     * 14 2 2
     * *** 3 3*
     *
     * work flow
     * firm
     * * Purchasing, Incoming goods, Incoming orders, Sales, Outgoing orders, Inventory, Warehouse management
     * Raw materials, Processing, Assembling, Products, Sales
     * * * * * */

    public SysAdmin sysAdmin;//Record logged in administrator information.

    public SysUser sysUser;//Record logged in user information.

    private SysAdminDao adminDao = new SysAdminDao();

    private SysUserDao userDao = new SysUserDao();

    private RechargeRecordDao recodeDao = new RechargeRecordDao();

    private BooksInfoDao booksInfoDao = new BooksInfoDao();

    private RentInfoDao rentInfoDao = new RentInfoDao();

    private Scanner input = new Scanner(System.in);

    BigDecimal ONEMonth = new BigDecimal("31");

    public void chooseLogin() {
        for (; ; ) {
            System.out.println("1. User access | 2. Admin access");
            String choose = input.next();
            if (choose.equals("2")) {
                Main.MENU = 2;
                break;
            } else if (choose.equals("1")) {
                Main.MENU = 4;
                break;

            }
        }
    }

    //User login
    public void userLogin() {
        for (; ; ) {
            System.out.println("Account: ");
            String name = input.next();
            System.out.println("Password: ");
            String pwd = input.next();
            SysUser user = userDao.login(name, pwd);
            if (user != null) {
                this.sysUser = user;
                Main.MENU = 5;
                break;
            } else {
                System.out.println("No account or wrong password.");
                System.out.println("Tap 'y' to continue: ");
                String choose = input.next();
                if (!choose.equals("y")) {
                    Main.MENU = 1;
                    break;
                }
            }
        }

    }

    //MainMenu
    public void userMainMenu() {
        for (; ; ) {
            System.out.println("1. Trade log; 2. Rent books; 3. User information; 4. Return books; 0. Turn back to login in admin");
            String choose = input.next();
            if (choose.equals("1")) {
                List<RechargeRecord> rechargeRecordList = recodeDao.findRecodeByUid(this.sysUser.getUid());
                if (rechargeRecordList.size() == 0) {
                    System.out.println("If you haven't topped up for your account yet, please do so now!");
                } else {
                    rechargeRecordList.forEach(u -> {
                        System.out.println(u);
                    });
                }

            } else if (choose.equals("3")) {
                System.out.println("User name\tReal name\t\tUser created time\t\t\t\t\tPhone\t\tBalance");
                System.out.println(sysUser.getUsername() + "\t\t" + sysUser.getRealname() + "\t\t\t" + sysUser.getCreatetime()
                        + "\t\t" + sysUser.getMobile() + "\t\t" + sysUser.getAmount());
            } else if (choose.equals("0")) {
                this.sysUser = null;//Log out
                Main.MENU = 1;
                break;
            } else if (choose.equals("2")) {
                booksInfoDao.findForALl().forEach(b -> {
                    System.out.println(b);
                });
                System.out.println("Please enter rental book's number: ");
                String bid = input.next();
                if (booksInfoDao.findForALl().stream().filter(f -> f.getBid().equals(bid)).count() > 0) {
                    RentInfo rentInfo = new RentInfo();
                    rentInfo.setUid(sysUser.getUid());
                    rentInfo.setBid(bid);
                    rentInfoDao.addRent(rentInfo);
                    System.out.println("Already posted your rental book request, waiting for administrator's approval, please be patient.");
                } else {
                    System.out.println("This number does not exist.");

                }
            } else if (choose.equals("4")) {
                // Search for the borrowed book linking to a uid.
                List<RentInfo> rentInfoList = rentInfoDao.getRentInfoById(sysUser.getUid());
                if (rentInfoList.size() == 0) {
                    System.out.println("You haven't borrowed a book, then go from now!");
                } else {
                    System.out.println("Your rental booklist: ");
                    System.out.println("Book number\t\tDaily rental\t\tDeposit\t\t\tRent days\t\t\tStart time\t\t\t\t\t\tDead line");

                    for (RentInfo rentInfo : rentInfoList) {
                        // To calculate how many days this book was borrowed.
                        Timestamp now = Timestamp.from(Instant.now());
                        Duration duration = Duration.between(rentInfo.getCreatetime().toInstant(), now.toInstant());
                        long days = duration.toDays() + 1;
                        System.out.println(rentInfo.getBid() + "\t\t\t" + rentInfo.getPrice() + "\t\t" + rentInfo.getDeposit()
                                + "\t\t" +days + "\t\t\t\t" + rentInfo.getCreatetime() + "\t\t" + rentInfo.getEndtime());
                    }
                    System.out.println("Should you wish to return the book forward, enter the book number. If incorrect, it will automatically be reversed to the previous action: ");
                    String tempBookId = input.next();
                    // Filter the ArrayList to find rental books records corresponding to the number entered by users.
                    List<RentInfo> collect = rentInfoList.stream().filter(r -> r.getBid().equals(tempBookId)).collect(Collectors.toList());
                    if (collect.size()>0){
                        RentInfo tempRentInfo = collect.get(0);

                        boolean b = rentInfoDao.ReturnBook(collect.get(0).getRid());
                        if (b){
                            System.out.println("Return books successfully.");
                            // Calculate rent days
                            Timestamp now = Timestamp.from(Instant.now());
                            Duration duration = Duration.between(tempRentInfo.getCreatetime().toInstant(), now.toInstant());
                            BigDecimal daysBigDe = BigDecimal.valueOf(duration.toDays() + 1);

                            BigDecimal multiply = tempRentInfo.getPrice().multiply(daysBigDe);// Calculate rental
                            userDao.reduceUserAmount(multiply,tempRentInfo.getUid());// Deduct rental
                            RechargeRecord rechargeRecord= new RechargeRecord();
                            rechargeRecord.setAmount(multiply.negate());
                            rechargeRecord.setUid(tempRentInfo.getUid());
                            rechargeRecord.setState(3);
                            recodeDao.payRent(rechargeRecord);
                        }else {
                            System.out.println("Failed to return books.");
                        }
                    }

                }
            }
        }

    }

    //Administrator login
    public void adminLogin() {
        for (; ; ) {
            System.out.println("Account: ");
            String name = input.next();
            System.out.println("Password: ");
            String pwd = input.next();
            SysAdmin admin = adminDao.adminLogin(name, pwd);
            if (admin != null) {
                this.sysAdmin = admin;
                Main.MENU = 3;
                break;
            } else {
                System.out.println("No account or wrong password.");
                System.out.println("Tap 'y' to continue: ");
                String choose = input.next();
                if (!choose.equals("y")) {
                    Main.MENU = 1;
                    break;
                }

            }
        }

    }

    public void adminMainMenu() {
        for (; ; ) {
            System.out.println("1. Recharge 2. Administor management 3. User management 4. add books 5. check books information 6. check rental books information 7. rent management 0. Turn back to login in user");
            String choose = input.next();
            if (choose.equals("4")) {
                try {
                    System.out.println("Bookname: ");
                    String bookname = input.next();
                    System.out.println("Price: ");
                    BigDecimal price = input.nextBigDecimal();
                    System.out.println("Description: ");
                    String description = input.next();
                    System.out.println("Author: ");
                    String author = input.next();
                    BooksInfo book = new BooksInfo();
                    book.setBookname(bookname);
                    book.setPrice(price);
                    book.setDescription(description);
                    book.setAuthor(author);
                    booksInfoDao.insert(book);
                } catch (Exception e) {
                    input = new Scanner(System.in);
                    System.out.println("Price entered illegal.");
                }
            } else if (choose.equals("5")) {
                booksInfoDao.findForALl().forEach(b -> {
                    System.out.println(b);
                });
            } else if (choose.equals("1")) {
                userDao.list().forEach(u -> {
                    System.out.println(u);
                });
                System.out.println("Enter your account for recharging.");
                String uid = input.next();
                try {
                    BigDecimal m;
                    while (true) {
                        System.out.println("Input your recharing amount: ");
                        m = input.nextBigDecimal();
                        int i = m.compareTo(BigDecimal.ZERO);
                        if (i > 0) {
                            break;
                        } else {
                            System.out.println("Input is not legal.");
                        }
                    }


                    String aid = this.sysAdmin.getAid();//recharge man;
                    RechargeRecord recode = new RechargeRecord();
                    recode.setUid(uid);
                    recode.setAmount(m);
                    recode.setAid(aid);
                    recode.setState(1);
                    recodeDao.addRecode(recode);//recharge record
                    userDao.updateUserAmount(m, uid);
                    System.out.println("Successfully recharged!");
                } catch (Exception e) {
                    System.out.println("Illegal amount.");
                    input = new Scanner(System.in);
                }
            } else if (choose.equals("0")) {
                this.sysAdmin = null;//exit logging
                Main.MENU = 1;
                break;
            } else if (choose.equals("7")) {
                List<RentInfo> rents = rentInfoDao.list();
                if (rents.size() == 0) {
                    System.out.println("There is no more rental information to manage.");
                } else {
                    rents.forEach(r -> {
                        System.out.println(r);
                    });
                    System.out.println("Input rent number: ");
                }


                String rid = input.next();
                //Filter the following sets of data into a new set based on the rent number entered by the user.
                List<RentInfo> collect = rents.stream().filter(r -> r.getRid().equals(rid)).collect(Collectors.toList());
                if (collect.size() > 0) {
                    RentInfo rentInfo = collect.get(0);
                    SysUser user = userDao.findById(rentInfo.getUid());//Get the user object to know the balance of this rental user.
                    try {
                        System.out.println("Input daily rental: ");
                        BigDecimal DailyRent = input.nextBigDecimal();
                        System.out.println("Input deposit: ");
                        BigDecimal deposit = input.nextBigDecimal();
                        //Compare balance plus deposit, and daily rental multiply 31

                        if (user.getAmount().compareTo(deposit.add(DailyRent.multiply(ONEMonth))) == 1) {
                            RentInfo r = new RentInfo();
                            r.setAid(sysAdmin.getAid());
                            r.setPrice(DailyRent);
                            r.setDeposit(deposit);
                            r.setRid(rid);
                            rentInfoDao.rentBook(r);//Successfully rent, complete the rental information.
                            userDao.reduceUserAmount(deposit, user.getUid());//deduct the balance of this user to pay deposit.
                            RechargeRecord record = new RechargeRecord();
                            record.setUid(user.getUid());
                            record.setAmount(deposit.negate());
                            record.setAid(sysAdmin.getAid());
                            recodeDao.payDeposit(record); //generate log with deposit.
                            System.out.println("Successfully rent.");
                        } else {
                            System.out.println("Not sufficient funds.");
                        }
                    } catch (Exception e) {
                        input = new Scanner(System.in);
                        System.out.println("Input amount illegal.");
                    }
                } else {
                    System.out.println("No rental information.");
                }
            } else if (choose.equals("2")) {
                List<SysAdmin> allAdmin = adminDao.findAllAdmin();
                System.out.println("Username\t\tRealname\t\tCreatetime\t\t\t\t\tMobile\t\t\t\tState(1. Normal 0. Unsubscribed)");
                if (allAdmin.size() > 0) {
                    for (SysAdmin admin : allAdmin) {
                        System.out.println(admin.getUsername() + "\t\t" + admin.getRealname() + "\t\t\t" + admin.getCreatetime()
                                + "\t\t" + admin.getMobile() + "\t\t" + admin.getState());
                    }
                }

                System.out.println("Tend to operation: ");
                System.out.println("1. Unsubscribe administrator 2. Subscribe administrator");
                String choose1 = input.next();
                if (choose1.equals("1")) {
                    System.out.println("Input the administratorname that you want to unsubscribe.");
                    String adminName = input.next();
                    SysAdmin admin = adminDao.getAdminByName(adminName);
                    if (admin == null) {
                        System.out.println("administratorname does not exist.");
                    } else {
                        boolean b = adminDao.deleteByName(adminName);
                        if (b) {
                            System.out.println("Unsubscribe done.");
                        } else {
                            System.out.println("Fail to unsubscribe.");
                        }
                    }
                } else if (choose1.equals("2")) {
                    SysAdmin admin = new SysAdmin();
                    System.out.println("Input your user number: ");
                    String newAdminId = input.next();
                    SysAdmin IsAdmin = adminDao.getAdminByName(newAdminId);
                    if (IsAdmin == null) {
                        admin.setAid(newAdminId);
                        System.out.println("Username: ");
                        admin.setUsername(input.next());
                        System.out.println("Password: ");
                        admin.setPassword(input.next());
                        System.out.println("Realname: ");
                        admin.setRealname(input.next());
                        System.out.println("Mobile: ");
                        admin.setMobile(input.next());
                        boolean b = adminDao.addAdmin(admin);
                        if (b) {
                            System.out.println("Subscribe successfully. ");
                        } else {
                            System.out.println("Fail to subscribe. ");
                        }
                    } else {
                        System.out.println("Error, the user you want to subscirbe that is exist. ");
                    }
                }

            } else if (choose.equals("3")) {
                List<SysUser> userList = new ArrayList<>();
                userList = userDao.findAllUser();
                String choose1;
                if (userList.size() > 0) {
                    for (SysUser user : userList) {
                        System.out.println(user);
                    }
                } else {
                    System.out.println("There is no user information. ");
                }

                System.out.println(Tend to operation: 1. Subscribe account  2. Unsubscribe account 3. Turn back to the previous operation.");
                choose1 = input.next();
                if (choose1.equals("1")) {

                    SysUser sysUser = new SysUser();
                    System.out.println("Input your user number.");
                    String NewUserId = input.next();
                    SysUser getUser = userDao.getUserById(NewUserId);
                    if (getUser == null) {
                        sysUser.setUid(NewUserId);
                        System.out.println("Username: ");
                        sysUser.setUsername(input.next());
                        System.out.println("Password");
                        sysUser.setPassword(input.next());
                        System.out.println("Realname: ");
                        sysUser.setRealname(input.next());
                        System.out.println("Mobile: ");
                        sysUser.setMobile(input.next());
                        sysUser.setAmount(BigDecimal.ZERO);
                        userDao.addSysUser(sysUser);
                        System.out.println("Subscribe successfully. ");
                    } else {
                        System.out.println("Error, the user you want to subscirbe that is exist. ");
                    }


                } else if (choose1.equals("2")) {
                    System.out.println("Input the user number of an account you want to unsubscribe: ");
                    String deleteUserId = input.next();
                    SysUser sysUser = userDao.getUserById(deleteUserId);
                    if (sysUser == null) {
                        System.out.println("Can not found the account, fail to unsubscribe. ");
                    } else {
                        userDao.deleteById(deleteUserId);
                        System.out.println("Unsubscribe successfully. ");
                    }
                }

            } else if (choose.equals("6")) {
                List<RentInfo> rentInfoList = new ArrayList<>();
                rentInfoList = rentInfoDao.getAllInfo();
                for (RentInfo rentInfo : rentInfoList) {
                    System.out.println(rentInfo);
                }
            }
        }

    }
}
