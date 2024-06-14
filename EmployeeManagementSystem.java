import java.util.*;

public class EmployeeManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<Integer, Employee> employees = new HashMap<>();
    static Map<Integer, Department> departments = new HashMap<>();
    static int employeeIdCounter = 1;
    static int departmentIdCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Employee Management");
            System.out.println("2. Hierarchy Management");
            System.out.println("3. Department Management");
            System.out.println("4. Performance Tracking");
            System.out.println("5. Security and Access Control");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    employeeManagementMenu();
                    break;
                case 2:
                    hierarchyManagementMenu();
                    break;
                case 3:
                    departmentManagementMenu();
                    break;
                case 4:
                    performanceTrackingMenu();
                    break;
                case 5:
                    securityMenu();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void employeeManagementMenu() {
        System.out.println("\nEmployee Management Menu:");
        System.out.println("1. Add new employee");
        System.out.println("2. Remove employee");
        System.out.println("3. Update employee information");
        System.out.println("4. Change employee department");
        System.out.println("5. Promote or demote employee");
        System.out.println("6. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                removeEmployee();
                break;
            case 3:
                updateEmployeeInformation();
                break;
            case 4:
                changeEmployeeDepartment();
                break;
            case 5:
                promoteOrDemoteEmployee();
                break;
            case 6:
                return; // Back to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee position: ");
        String position = scanner.nextLine();
        System.out.print("Enter employee department ID: ");
        int departmentId = scanner.nextInt();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Department department = departments.get(departmentId);
        if (department == null) {
            System.out.println("Invalid department ID.");
            return;
        }

        Employee newEmployee = new Employee(name, employeeIdCounter++, position, department.getName(), salary);
        employees.put(newEmployee.getId(), newEmployee);
        department.addEmployee(newEmployee);
        System.out.println("Employee added successfully.");
    }

    private static void removeEmployee() {
        System.out.print("Enter employee ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        if (employee != null) {
            employees.remove(id);
            Department department = departments.get(employee.getDepartmentId());
            if (department != null) {
                department.removeEmployee(employee);
            }
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateEmployeeInformation() {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        if (employee != null) {
            System.out.print("Enter new position (leave blank to keep unchanged): ");
            String position = scanner.nextLine();
            if (!position.isEmpty()) {
                employee.setPosition(position);
            }

            System.out.print("Enter new salary (leave blank to keep unchanged): ");
            String salaryStr = scanner.nextLine();
            if (!salaryStr.isEmpty()) {
                double salary = Double.parseDouble(salaryStr);
                employee.setSalary(salary);
            }

            System.out.println("Employee information updated successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void changeEmployeeDepartment() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new department ID: ");
        int newDepartmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        Department newDepartment = departments.get(newDepartmentId);
        if (employee != null && newDepartment != null) {
            Department oldDepartment = departments.get(employee.getDepartmentId());
            if (oldDepartment != null) {
                oldDepartment.removeEmployee(employee);
            }
            newDepartment.addEmployee(employee);
            employee.setDepartment(newDepartment.getName());
            System.out.println("Employee department changed successfully.");
        } else {
            System.out.println("Employee or department not found.");
        }
    }

    private static void promoteOrDemoteEmployee() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new position: ");
        String newPosition = scanner.next();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        if (employee != null) {
            employee.setPosition(newPosition);
            System.out.println("Employee promoted/demoted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void hierarchyManagementMenu() {
        System.out.println("\nHierarchy Management Menu:");
        System.out.println("1. Find supervisor");
        System.out.println("2. Find subordinates");
        System.out.println("3. Analyze organizational structure");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                findSupervisor();
                break;
            case 2:
                findSubordinates();
                break;
            case 3:
                analyzeStructure();
                break;
            case 4:
                return; // Back to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void findSupervisor() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        if (employee != null && employee.getSupervisor() != null) {
            System.out.println("Supervisor: " + employee.getSupervisor().getName());
        } else {
            System.out.println("Supervisor not found.");
        }
    }

    private static void findSubordinates() {
        System.out.print("Enter supervisor ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee supervisor = employees.get(id);
        if (supervisor != null) {
            List<Employee> subordinates = supervisor.getSubordinates();
            if (!subordinates.isEmpty()) {
                System.out.println("Subordinates:");
                for (Employee subordinate : subordinates) {
                    System.out.println(subordinate.getName());
                }
            } else {
                System.out.println("No subordinates found.");
            }
        } else {
            System.out.println("Supervisor not found.");
        }
    }

    private static void analyzeStructure() {
        System.out.println("Analyzing organizational structure...");
        // Implement the logic for analyzing the organizational structure
        // For example, calculate and print the hierarchy depth
    }

    private static void departmentManagementMenu() {
        System.out.println("\nDepartment Management Menu:");
        System.out.println("1. Create department");
        System.out.println("2. Assign employee to department");
        System.out.println("3. Manage department budget");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                createDepartment();
                break;
            case 2:
                assignEmployeeToDepartment();
                break;
            case 3:
                manageDepartmentBudget();
                break;
            case 4:
                return; // Back to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void createDepartment() {
        System.out.print("Enter department name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Department newDepartment = new Department(name, departmentIdCounter++, budget);
        departments.put(newDepartment.getId(), newDepartment);
        System.out.println("Department created successfully.");
    }

    private static void assignEmployeeToDepartment() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        Department department = departments.get(departmentId);
        if (employee != null && department != null) {
            department.addEmployee(employee);
            employee.setDepartment(department.getName());
            System.out.println("Employee assigned to department successfully.");
        } else {
            System.out.println("Employee or department not found.");
        }
    }

    private static void manageDepartmentBudget() {
        System.out.print("Enter department ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Department department = departments.get(id);
        if (department != null) {
            department.setBudget(budget);
            System.out.println("Department budget updated successfully.");
        } else {
            System.out.println("Department not found.");
        }
    }

    private static void performanceTrackingMenu() {
        System.out.println("\nPerformance Tracking Menu:");
        System.out.println("1. Track employee performance");
        System.out.println("2. Generate performance reports");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                trackPerformance();
                break;
            case 2:
                generatePerformanceReports();
                break;
            case 3:
                return; // Back to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void trackPerformance() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Employee employee = employees.get(id);
        if (employee != null) {
            System.out.print("Enter performance metric name: ");
            String metricName = scanner.nextLine();
            System.out.print("Enter performance metric value: ");
            int metricValue = scanner.nextInt();
            scanner.nextLine(); // consume newline

            employee.addPerformanceMetric(metricName, metricValue);
            System.out.println("Performance tracked successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void generatePerformanceReports() {
        System.out.println("Generating performance reports...");
        for (Employee employee : employees.values()) {
            System.out.println("Employee: " + employee.getName());
            System.out.println("Performance Metrics: " + employee.getPerformanceMetrics());
        }
    }

    private static void securityMenu() {
        System.out.println("\nSecurity and Access Control Menu:");
        System.out.println("1. Authenticate user");
        System.out.println("2. Change login credentials");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                authenticateUser();
                break;
            case 2:
                changeLoginCredentials();
                break;
            case 3:
                return; // Back to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void authenticateUser() {
        // Implement authentication logic here
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Simulate authentication process
        if ("admin".equals(username) && "admin123".equals(password)) {
            System.out.println("Authentication successful.");
        } else {
            System.out.println("Authentication failed.");
        }
    }

    private static void changeLoginCredentials() {
        // Implement change login credentials logic here
        System.out.print("Enter old password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        // Simulate change password process
        if ("admin123".equals(oldPassword)) {
            // Change password logic here
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }
}

class Employee {
    private String name;
    private int id;
    private String position;
    private String department;
    private double salary;
    private Employee supervisor;
    private List<Employee> subordinates;
    private Map<String, Integer> performanceMetrics;

    public Employee(String name, int id, String position, String department, double salary) {
        this.name = name;
        this.id = id;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.subordinates = new ArrayList<>();
        this.performanceMetrics = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
    }

    public void removeSubordinate(Employee subordinate) {
        subordinates.remove(subordinate);
    }

    public void addPerformanceMetric(String metricName, int metricValue) {
        performanceMetrics.put(metricName, metricValue);
    }

    public Map<String, Integer> getPerformanceMetrics() {
        return performanceMetrics;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentId() {
        return department;
    }
}

class Department {
    private String name;
    private int id;
    private double budget;
    private List<Employee> employees;

    public Department(String name, int id, double budget) {
        this.name = name;
        this.id = id;
        this.budget = budget;
        this.employees = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
}

class PerformanceTracking {
    private Map<Integer, Map<String, Integer>> performanceData = new HashMap<>();

    public void trackPerformance(int employeeId, String metricName, int metricValue) {
        performanceData.putIfAbsent(employeeId, new HashMap<>());
        performanceData.get(employeeId).put(metricName, metricValue);
    }

    public Map<String, Integer> getPerformanceMetrics(int employeeId) {
        return performanceData.getOrDefault(employeeId, new HashMap<>());
    }

    public void generateReports() {
        for (Map.Entry<Integer, Map<String, Integer>> entry : performanceData.entrySet()) {
            int employeeId = entry.getKey();
            Map<String, Integer> metrics = entry.getValue();
            System.out.println("Employee ID: " + employeeId);
            System.out.println("Performance Metrics: " + metrics);
        }
    }
}
