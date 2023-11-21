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
                    System.out.println("没有租赁信息需要管理");
                } else {
                    rents.forEach(r -> {
                        System.out.println(r);
                    });
                    System.out.println("请输入租赁编号");
                }


                String rid = input.next();
                //根据用户输入的租赁编号 过滤以下集合是数据 变成一个新的集合
                List<RentInfo> collect = rents.stream().filter(r -> r.getRid().equals(rid)).collect(Collectors.toList());
                if (collect.size() > 0) {
                    RentInfo rentInfo = collect.get(0);
                    SysUser user = userDao.findById(rentInfo.getUid());//获取用户对象 然后拿到该租赁用户的余额
                    try {
                        System.out.println("请输入日租金");
                        BigDecimal DailyRent = input.nextBigDecimal();
                        System.out.println("请输入押金");
                        BigDecimal deposit = input.nextBigDecimal();
                        // 余额和押金+日租金*31 比对大小

                        if (user.getAmount().compareTo(deposit.add(DailyRent.multiply(ONEMonth))) == 1) {
                            RentInfo r = new RentInfo();
                            r.setAid(sysAdmin.getAid());
                            r.setPrice(DailyRent);
                            r.setDeposit(deposit);
                            r.setRid(rid);
                            rentInfoDao.rentBook(r);//租赁成功 将租赁信息信息补充完整
                            userDao.reduceUserAmount(deposit, user.getUid());//减少租赁用户的余额来支付押金
                            RechargeRecord record = new RechargeRecord();
                            record.setUid(user.getUid());
                            record.setAmount(deposit.negate());
                            record.setAid(sysAdmin.getAid());
                            recodeDao.payDeposit(record); //生成支付押金的支付记录
                            System.out.println("租赁成功！");
                        } else {
                            System.out.println("余额不足");
                        }
                    } catch (Exception e) {
                        input = new Scanner(System.in);
                        System.out.println("输入金额不合法");
                    }
                } else {
                    System.out.println("没有这条租赁信息");
                }
            } else if (choose.equals("2")) {
                List<SysAdmin> allAdmin = adminDao.findAllAdmin();
                System.out.println("用户名\t\t真实姓名\t\t用户创建时间\t\t\t\t\t电话\t\t\t\t状态(1表示正常，0表示已注销)");
                if (allAdmin.size() > 0) {
                    for (SysAdmin admin : allAdmin) {
                        System.out.println(admin.getUsername() + "\t\t" + admin.getRealname() + "\t\t\t" + admin.getCreatetime()
                                + "\t\t" + admin.getMobile() + "\t\t" + admin.getState());
                    }
                }

                System.out.println("请输入你想要的操作：");
                System.out.println("1.删除管理员 2.添加管理员");
                String choose1 = input.next();
                if (choose1.equals("1")) {
                    System.out.println("请输入要删除的管理员用户名");
                    String adminName = input.next();
                    SysAdmin admin = adminDao.getAdminByName(adminName);
                    if (admin == null) {
                        System.out.println("用户名不存在");
                    } else {
                        boolean b = adminDao.deleteByName(adminName);
                        if (b) {
                            System.out.println("删除成功");
                        } else {
                            System.out.println("删除失败");
                        }
                    }
                } else if (choose1.equals("2")) {
                    SysAdmin admin = new SysAdmin();
                    System.out.println("请输入用户id");
                    String newAdminId = input.next();
                    SysAdmin IsAdmin = adminDao.getAdminByName(newAdminId);
                    if (IsAdmin == null) {
                        admin.setAid(newAdminId);
                        System.out.println("请输入用户名");
                        admin.setUsername(input.next());
                        System.out.println("请输入用户密码");
                        admin.setPassword(input.next());
                        System.out.println("请输入真实姓名");
                        admin.setRealname(input.next());
                        System.out.println("请输入电话");
                        admin.setMobile(input.next());
                        boolean b = adminDao.addAdmin(admin);
                        if (b) {
                            System.out.println("添加成功!");
                        } else {
                            System.out.println("添加失败!");
                        }
                    } else {
                        System.out.println("添加失败！该用户已经存在！");
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
                    System.out.println("还没有用户信息！");
                }

                System.out.println("请选择你的操作：1.添加用户  2.删除用户 3.返回上一级");
                choose1 = input.next();
                if (choose1.equals("1")) {

                    SysUser sysUser = new SysUser();
                    System.out.println("请输入用户id");
                    String NewUserId = input.next();
                    SysUser getUser = userDao.getUserById(NewUserId);
                    if (getUser == null) {
                        sysUser.setUid(NewUserId);
                        System.out.println("请输入用户名");
                        sysUser.setUsername(input.next());
                        System.out.println("请输入用户密码");
                        sysUser.setPassword(input.next());
                        System.out.println("请输入真实姓名");
                        sysUser.setRealname(input.next());
                        System.out.println("请输入电话");
                        sysUser.setMobile(input.next());
                        sysUser.setAmount(BigDecimal.ZERO);
                        userDao.addSysUser(sysUser);
                        System.out.println("添加成功！");
                    } else {
                        System.out.println("该用户id已经存在！");
                    }


                } else if (choose1.equals("2")) {
                    System.out.println("请输入要删除的用户id");
                    String deleteUserId = input.next();
                    SysUser sysUser = userDao.getUserById(deleteUserId);
                    if (sysUser == null) {
                        System.out.println("用户未找到，删除失败！");
                    } else {
                        userDao.deleteById(deleteUserId);
                        System.out.println("删除成功！");
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
