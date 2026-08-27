let problems = [];


// ==========================================
// LOAD PROBLEMS
// ==========================================

async function loadProblems() {

    try {

        const response =
            await fetch("api/problems");


        if (!response.ok) {

            throw new Error(
                "Failed to load problems"
            );
        }


        problems =
            await response.json();


        renderProblems();


    } catch (error) {

        console.error(error);


        document.getElementById(
            "problems"
        ).innerHTML =
            "<p>Could not load problems.</p>";
    }
}


// ==========================================
// ADD PROBLEM
// ==========================================

async function addProblem() {


    const contest =
        document.getElementById(
            "newContest"
        ).value.trim();


    const index =
        document.getElementById(
            "newIndex"
        ).value.trim();


    const name =
        document.getElementById(
            "newName"
        ).value.trim();


    const rating =
        document.getElementById(
            "newRating"
        ).value.trim();


    const file =
        document.getElementById(
            "newFile"
        ).value.trim();


    if (
        !contest ||
        !index ||
        !name ||
        !rating
    ) {

        alert(
            "Please fill all required fields."
        );

        return;
    }


    const formData =
        new URLSearchParams();


    formData.append(
        "contestId",
        contest
    );


    formData.append(
        "index",
        index
    );


    formData.append(
        "name",
        name
    );


    formData.append(
        "rating",
        rating
    );


    formData.append(
        "file",
        file
    );


    try {


        const response =
            await fetch(
                "api/problems",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/x-www-form-urlencoded"
                    },

                    body: formData
                }
            );


        const result =
            await response.json();


        if (!response.ok) {

            alert(
                "Error: " +
                (
                    result.error ||
                    "Could not add problem"
                )
            );

            return;
        }


        // Clear form

        document.getElementById(
            "newContest"
        ).value = "";


        document.getElementById(
            "newIndex"
        ).value = "";


        document.getElementById(
            "newName"
        ).value = "";


        document.getElementById(
            "newRating"
        ).value = "";


        document.getElementById(
            "newFile"
        ).value = "";


        // Reload from database

        await loadProblems();


        alert(
            "Problem added successfully!"
        );


    } catch (error) {

        console.error(error);


        alert(
            "Could not connect to server."
        );
    }
}


// ==========================================
// DELETE PROBLEM
// ==========================================

async function deleteProblem(id) {


    const confirmed =
        confirm(
            "Are you sure you want to delete this problem?"
        );


    if (!confirmed) {
        return;
    }


    try {


        const response =
            await fetch(
                `api/problems?id=${id}`,
                {
                    method: "DELETE"
                }
            );


        const result =
            await response.json();


        if (!response.ok) {

            alert(
                "Delete failed: " +
                (
                    result.error ||
                    "Unknown error"
                )
            );

            return;
        }


        await loadProblems();


        alert(
            "Problem deleted successfully!"
        );


    } catch (error) {

        console.error(error);


        alert(
            "Could not connect to server."
        );
    }
}


// ==========================================
// DISPLAY PROBLEMS
// ==========================================

function renderProblems() {


    const container =
        document.getElementById(
            "problems"
        );


    container.innerHTML = "";


    if (problems.length === 0) {

        container.innerHTML =
            "<p>No problems found.</p>";

        updateStats();

        return;
    }


    problems.forEach(problem => {


        const div =
            document.createElement(
                "div"
            );


        div.className =
            "problem";


        div.innerHTML = `

            <h3>
                ${problem.contestId}${problem.index}
                - ${problem.name}
            </h3>

            <p>
                <strong>Rating:</strong>
                ${problem.rating}
            </p>

            <p>
                <strong>Solution:</strong>
                ${problem.file || "-"}
            </p>

            <button
                class="delete"
                onclick="deleteProblem(${problem.id})"
            >
                Delete
            </button>

        `;


        container.appendChild(div);

    });


    updateStats();
}


// ==========================================
// STATISTICS
// ==========================================

function updateStats() {


    const stats =
        document.getElementById(
            "stats"
        );


    const ratingCount = {};


    problems.forEach(problem => {


        if (!problem.rating) {
            return;
        }


        const rating =
            parseInt(
                problem.rating
            );


        ratingCount[rating] =
            (
                ratingCount[rating] || 0
            ) + 1;

    });


    let html = `

        <div class="stat">

            <strong>Total</strong>

            <br>

            ${problems.length}

        </div>

    `;


    Object.keys(ratingCount)
        .sort(
            (a, b) => a - b
        )
        .forEach(rating => {


            html += `

                <div class="stat">

                    <strong>
                        ${rating}
                    </strong>

                    <br>

                    ${ratingCount[rating]}
                    solved

                </div>

            `;

        });


    stats.innerHTML =
        html;
}


// ==========================================
// GENERATE README
// ==========================================

function generateREADME() {


    const handle =
        document.getElementById(
            "handle"
        ).value;


    const github =
        document.getElementById(
            "github"
        ).value;


    let markdown = "";


    markdown +=
        `# ${handle} - Codeforces Solutions\n\n`;


    markdown +=
        `My Codeforces problem-solving journey using Java.\n\n`;


    markdown +=
        `**Codeforces:** [${handle}](https://codeforces.com/profile/${handle})  \n`;


    markdown +=
        `**GitHub:** [Repository](${github})\n\n`;


    // Statistics

    markdown +=
        `## 📊 Statistics\n\n`;


    markdown +=
        `**Total Problems Solved:** ${problems.length}\n\n`;


    const ratingCount = {};


    problems.forEach(problem => {


        if (!problem.rating) {
            return;
        }


        const rating =
            parseInt(
                problem.rating
            );


        ratingCount[rating] =
            (
                ratingCount[rating] || 0
            ) + 1;

    });


    markdown +=
        `| Rating | Solved |\n`;


    markdown +=
        `|-------:|-------:|\n`;


    Object.keys(ratingCount)
        .sort(
            (a, b) => a - b
        )
        .forEach(rating => {


            markdown +=
                `| ${rating} | ${ratingCount[rating]} |\n`;

        });


    // Problems

    markdown +=
        `\n## 🧩 Problems\n\n`;


    markdown +=
        `| # | Problem | Rating | Solution |\n`;


    markdown +=
        `|---|---|---:|---|\n`;


    const sorted =
        [...problems].sort(
            (a, b) =>
                parseInt(a.contestId) -
                parseInt(b.contestId)
        );


    sorted.forEach(problem => {


        const number =
            `${problem.contestId}${problem.index}`;


        let solution = "-";


        if (problem.file) {

            solution =
                `[Java](${problem.file})`;
        }


        markdown +=
            `| ${number} | ${problem.name} | ${problem.rating} | ${solution} |\n`;

    });


    // Rating progress

    markdown +=
        `\n## 📚 Rating Progress\n\n`;


    markdown +=
        `| Rating | Problems |\n`;


    markdown +=
        `|-------:|---------:|\n`;


    Object.keys(ratingCount)
        .sort(
            (a, b) => a - b
        )
        .forEach(rating => {


            const count =
                ratingCount[rating];


            const bar =
                "█".repeat(
                    Math.min(
                        count,
                        30
                    )
                );


            markdown +=
                `| ${rating} | ${bar} ${count} |\n`;

        });


    markdown +=
        `\n---\n\n`;


    markdown +=
        `*Solutions are written and maintained by ${handle}.*\n`;


    document.getElementById(
        "output"
    ).value =
        markdown;
}


// ==========================================
// COPY README
// ==========================================

function copyREADME() {


    const output =
        document.getElementById(
            "output"
        );


    navigator.clipboard.writeText(
        output.value
    );


    alert(
        "README copied!"
    );
}


// ==========================================
// START APPLICATION
// ==========================================

document.addEventListener(
    "DOMContentLoaded",
    loadProblems
);
