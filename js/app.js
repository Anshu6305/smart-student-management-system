/**
 * FRONTEND-ONLY Student Management System
 * No backend / Tomcat required
 */

document.addEventListener('DOMContentLoaded', function () {

    // ============================================================
    // DOM ELEMENTS
    // ============================================================

    const studentForm = document.getElementById('studentForm');
    const studentTableBody = document.getElementById('studentTableBody');
    const formAlert = document.getElementById('formAlert');

    const globalSearch = document.getElementById('globalSearch');
    const branchFilter = document.getElementById('branchFilter');

    const statTotal = document.getElementById('statTotal');
    const statCGPA = document.getElementById('statCGPA');
    const statAttendance = document.getElementById('statAttendance');
    const statBranches = document.getElementById('statBranches');

    const toastContainer = document.getElementById('toastContainer');

    // ============================================================
    // STUDENT DATA
    // ============================================================

    let students = [

        {
            id: 1,
            name: "Anshuman Routray",
            rollNumber: "23052783",
            branch: "Computer Science",
            semester: 6,
            email: "anshuman@kiit.ac.in",
            phone: "6372843128",
            attendance: 85,
            cgpa: 7.0
        },

        {
            id: 2,
            name: "Anusha Srivastava",
            rollNumber: "23052784",
            branch: "Computer Science",
            semester: 6,
            email: "23052784@kiit.ac.in",
            phone: "9123456780",
            attendance: 92,
            cgpa: 8.8
        },

        {
            id: 3,
            name: "Pratyush Mohanty",
            rollNumber: "23053320",
            branch: "Electronics",
            semester: 5,
            email: "23053320@kiit.ac.in",
            phone: "9234567810",
            attendance: 88,
            cgpa: 8.1
        },

        {
            id: 4,
            name: "Arvind Yadav",
            rollNumber: "23052786",
            branch: "Mechanical",
            semester: 4,
            email: "23052786@kiit.ac.in",
            phone: "9345678120",
            attendance: 79,
            cgpa: 7.4
        },

        {
            id: 5,
            name: "Tanisha Behura",
            rollNumber: "2305663",
            branch: "Civil",
            semester: 3,
            email: "2305663@kiit.ac.in",
            phone: "7008626182",
            attendance: 90,
            cgpa: 8.6
        },

        {
            id: 6,
            name: "Aditya Kumar Sahu",
            rollNumber: "23052780",
            branch: "Computer Science",
            semester: 6,
            email: "23052780@kiit.ac.in",
            phone: "9567812340",
            attendance: 83,
            cgpa: 7.8
        },

        {
            id: 7,
            name: "Aman Verma",
            rollNumber: "23052789",
            branch: "Electrical",
            semester: 5,
            email: "aman@kiit.ac.in",
            phone: "9678123450",
            attendance: 76,
            cgpa: 6.9
        },

        {
            id: 8,
            name: "Priya Sharma",
            rollNumber: "23052790",
            branch: "Information Technology",
            semester: 6,
            email: "23052790@kiit.ac.in",
            phone: "9781234560",
            attendance: 95,
            cgpa: 9.2
        },

        {
            id: 9,
            name: "Rahul Singh",
            rollNumber: "23052791",
            branch: "Computer Science",
            semester: 2,
            email: "23052791@kiit.ac.in",
            phone: "9892345670",
            attendance: 81,
            cgpa: 7.5
        },

        {
            id: 10,
            name: "Sneha Patra",
            rollNumber: "23052792",
            branch: "Biotechnology",
            semester: 4,
            email: "23052792@kiit.ac.in",
            phone: "9903456781",
            attendance: 87,
            cgpa: 8.0
        }

    ];

    // ============================================================
    // INITIAL LOAD
    // ============================================================

    renderStudents(students);
    updateDashboard();

    // ============================================================
    // ADD STUDENT
    // ============================================================

    studentForm.addEventListener('submit', function (e) {

        e.preventDefault();

        const student = {
            id: Date.now(),
            name: document.getElementById('fName').value,
            rollNumber: document.getElementById('fRoll').value,
            branch: document.getElementById('fBranch').value,
            semester: document.getElementById('fSemester').value,
            email: document.getElementById('fEmail').value,
            phone: document.getElementById('fPhone').value,
            attendance: document.getElementById('fAttendance').value,
            cgpa: document.getElementById('fCGPA').value
        };

        students.push(student);

        renderStudents(students);
        updateDashboard();

        showToast('Student added successfully!', 'success');

        studentForm.reset();
    });

    // ============================================================
    // RENDER STUDENTS
    // ============================================================

    function renderStudents(studentList) {

        if (studentList.length === 0) {

            studentTableBody.innerHTML = `
                <tr>
                    <td colspan="9">No students found</td>
                </tr>
            `;

            return;
        }

        let html = '';

        studentList.forEach(student => {

            html += `
                <tr>
                    <td>${student.name}</td>
                    <td>${student.rollNumber}</td>
                    <td>${student.branch}</td>
                    <td>${student.semester}</td>
                    <td>${student.email}</td>
                    <td>${student.phone}</td>
                    <td>${student.attendance}%</td>
                    <td>${student.cgpa}</td>
                    <td>
                        <button onclick="deleteStudent(${student.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });

        studentTableBody.innerHTML = html;
    }

    // ============================================================
    // DELETE STUDENT
    // ============================================================

    window.deleteStudent = function (id) {

        students = students.filter(student => student.id !== id);

        renderStudents(students);

        updateDashboard();

        showToast('Student deleted successfully!', 'success');
    };

    // ============================================================
    // SEARCH + BRANCH FILTER
    // ============================================================

    function filterStudents() {

        const keyword = globalSearch.value.toLowerCase();
        const selectedBranch = branchFilter.value;

        let filtered = students.filter(student => {

            const matchesSearch =
                student.name.toLowerCase().includes(keyword) ||
                student.rollNumber.toLowerCase().includes(keyword) ||
                student.branch.toLowerCase().includes(keyword);

            const matchesBranch =
                selectedBranch === '' ||
                student.branch === selectedBranch;

            return matchesSearch && matchesBranch;
        });

        renderStudents(filtered);
    }

    globalSearch.addEventListener('input', filterStudents);

    branchFilter.addEventListener('change', filterStudents);

    // ============================================================
    // DASHBOARD STATS
    // ============================================================

    function updateDashboard() {

        statTotal.textContent = students.length;

        let totalCGPA = 0;
        let totalAttendance = 0;

        const branches = new Set();

        students.forEach(student => {

            totalCGPA += parseFloat(student.cgpa);

            totalAttendance += parseFloat(student.attendance);

            branches.add(student.branch);
        });

        statCGPA.textContent =
            students.length > 0
                ? (totalCGPA / students.length).toFixed(2)
                : '0';

        statAttendance.textContent =
            students.length > 0
                ? (totalAttendance / students.length).toFixed(1) + '%'
                : '0%';

        statBranches.textContent = branches.size;
    }

    // ============================================================
    // TOAST
    // ============================================================

    function showToast(message, type) {

        if (!toastContainer) {
            alert(message);
            return;
        }

        const toast = document.createElement('div');

        toast.className = 'toast';

        toast.innerHTML = message;

        toastContainer.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);
    }

});

// ==========================================
// SIMPLE VIEW SWITCHING
// ==========================================

const dashboardView = document.getElementById('viewDashboard');
const studentsView = document.getElementById('viewStudents');
const addView = document.getElementById('viewForm');

document.querySelector('[data-view="dashboard"]').onclick = function () {

    dashboardView.classList.add('active');
    studentsView.classList.remove('active');
    addView.classList.remove('active');
};

document.querySelector('[data-view="students"]').onclick = function () {

    dashboardView.classList.remove('active');
    studentsView.classList.add('active');
    addView.classList.remove('active');
};

document.querySelector('[data-view="addStudent"]').onclick = function () {

    dashboardView.classList.remove('active');
    studentsView.classList.remove('active');
    addView.classList.add('active');
};