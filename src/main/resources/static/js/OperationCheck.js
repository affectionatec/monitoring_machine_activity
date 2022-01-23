let initOptionList = ()=>{
    $.ajax({
        url: '/checkItemAll',
        type: 'get',
        contentType: 'application/json',
        success: (res) => {
            res=JSON.parse(res)
            if(typeof res == "string"){
                alert('res')
            }
            if(typeof res == "object"){
                mountOptions(res);
            }
        },
        error: (er) => {
            console.log('fail.')
        }
    })
}




// modal show
let callModal = () => {
    $('.option-edit-panel').html(`
        <label for="optionName">Option Name:</label>
        <input type="text" name="optionName" /><br />
        <label for="standardType">Standard Type:</label>
        <input type="radio" name='standardType' value="0" />Equal
        <input type="radio" name='standardType' value="1" />Scope
        <br />
        <div class="standard-value-container">
        </div>
    `);

    $('input:radio[name="standardType"][value="0"]').attr("checked", true);

    $('.standard-value-container').html(`
    <label class="equal-type" for="equalType">Standard Value:</label>
        <input type="radio" name='equalType' value="1" />On
        <input type="radio" name='equalType' value="0" />Down
    `)
    $('.modal-footer').html(`
    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" onclick="addNewOption()">Save</button>
    `)
    $('#newModalLabel').html('Add a new option');
    $('#newFormElementPanel').modal('show');
    $('input:radio[name="standardType"]').click(() => {
        let checkValue = $('input:radio[name="standardType"]:checked').val();
        if (checkValue == '0') {
            $('.standard-value-container').html(`
            <label class="equal-type" for="equalType">Standard Value:</label>
            <input type="radio" name='equalType' value="1" />On
            <input type="radio" name='equalType' value="0" />Down
            `)
        } else if (checkValue == '1') {

            $('.standard-value-container').html(`
            <label class="scope-type" for="scopeType">Standard Value:</label>
            <input class="scope-type" id="scopeStart" type="text" /><span class="scope-type"> ~ </span><input class="scope-type" id="scopeEnd" type="text" />
            `)
        }
    });
}

let checkEmpty = () => {
    $(".option-input-error-tips").remove();
    let emptyFlag = 1;
    let optionName = $(':input[name="optionName"]').val();
    if (optionName == "" || !optionName) {
        emptyFlag = 0;
        $(':input[name="optionName"]').after(`<span class="option-input-error-tips" style="margin: 1rem; color: red">Option name should not be empty.</span>`);
    }
    let standardType = $("input:radio[name='standardType']:checked").val();
    if (standardType == '0') {
        equalValue = $("input:radio[name='equalType']:checked").val();
        if (!equalValue) {
            emptyFlag = 0;
            $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">Please select an option.</p >`);
        }
        // standardData['value'] = equalValue;
    } else if (standardType == '1') {
        let scopeStart = parseInt($('#scopeStart').val());
        let scopeEnd = parseInt($('#scopeEnd').val());
        console.log(">>>", isNaN(scopeStart), isNaN(scopeEnd))
        if (!$('#scopeStart').val()) {
            emptyFlag = 0;
            $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">Please input start value.</p >`);
        } else if (isNaN(scopeStart)) {
            emptyFlag = 0;
            $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">Please input number for start value.</p >`);
        }
        if (!$('#scopeEnd').val()) {
            emptyFlag = 0;
            $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">Please input end value.</p >`);
        } else if (isNaN(scopeEnd)) {
            emptyFlag = 0;
            $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">Please input number for end value.</p >`);
        }
        if (!isNaN(scopeStart) && !isNaN(scopeEnd)) {
            if (parseInt(scopeStart) >= parseInt(scopeEnd)) {
                emptyFlag = 0;
                $('.standard-value-container').append(`<p class="option-input-error-tips" style="margin: 0 0 0 8.5rem; color: red">End value should be larger than start.</p >`);
            }
        }
    }

    return emptyFlag;
}


// Add new option
let addNewOption = () => {
    let optionName = $(':input[name="optionName"]').val();
    let standardType = $("input:radio[name='standardType']:checked").val();
    let equalValue = 0;
    let scopeValues = [];
    let standardData = {
        name: optionName,
        mode: standardType,
    }
    if (standardType == '0') {
        equalValue = $("input:radio[name='equalType']:checked").val();
        standardData['value'] = equalValue;
    }
    else if (standardType == '1') {
        scopeValues.push($('#scopeStart').val());
        scopeValues.push($('#scopeEnd').val());
        standardData['value'] = scopeValues;
        // customedOptions.push({
        //     optionName: optionName,
        //     mode: standardType,
        //     value: scopeValues
        // })
    }
    console.log(JSON.stringify(standardData));
    if (!checkEmpty()) { return; }
    $.ajax({
        url: '/checkItemAdd',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(standardData),
        success: (res) => {
            res=JSON.parse(res)
            console.log(res)
            if(typeof res == "string"){
                alert('res')
            }
            if(typeof res == "object"){
                mountOptions(res);
            }
        },
        error: (er) => {
            console.log('fail.')
        }
    })
    $('#newFormElementPanel').modal('hide');
}
// edit option
let editOption = (id)=>{
    let optionName = $(':input[name="optionName"]').val();
    let standardType = $("input:radio[name='standardType']:checked").val();
    let equalValue = 0;
    let scopeValues = [];
    let standardData = {
        'id':id,
        'name': optionName,
        'mode': standardType,
    }
    if (standardType == '0') {
        equalValue = $("input:radio[name='equalType']:checked").val();
        standardData['value'] = equalValue;
    } else if (standardType == '1') {
        scopeValues.push($('#scopeStart').val());
        scopeValues.push($('#scopeEnd').val());
        standardData['value'] = scopeValues;
    }
    console.log(JSON.stringify(standardData));
    if (!checkEmpty()) { return; }
    $.ajax({
        url: '/checkItemEdit',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(standardData),
        success: (res) => {
            res=JSON.parse(res)
            mountOptions(res);
        },
        error: (er) => {
            console.log('fail.')
        }
    })
    $('#newFormElementPanel').modal('hide');
}
// call edit modal
let callEditModal = (optionName, mode, value, id) => {
    $('.option-edit-panel').html(`
        <label for="optionName">Option Name:</label>
        <input type="text" name="optionName" value="${optionName}"/><br />
        <label for="standardType">Standard Type:</label>
        <input type="radio" name='standardType' value="0" />Equal
        <input type="radio" name='standardType' value="1" />Scope
        <br />
        <div class="standard-value-container">
        </div>
    `);
    if(mode == '0'){
        $('input:radio[name="standardType"][value="0"]').attr("checked", true);
        $('.standard-value-container').html(`
        <label class="equal-type" for="equalType">Standard Value:</label>
        <input type="radio" name='equalType' value="1" />On
        <input type="radio" name='equalType' value="0" />Down
        `)
        if(value == 0){
            $('input:radio[name="equalType"][value="0"]').attr("checked", true);
        } else if(value == 1){
            $('input:radio[name="equalType"][value="1"]').attr("checked", true);
        }
    } else if(mode == '1'){
        $('input:radio[name="standardType"][value="1"]').attr("checked", true);
        $('.standard-value-container').html(`
        <label class="scope-type" for="scopeType">Standard Value:</label>
        <input class="scope-type" id="scopeStart" type="text" value="${value.split(',')[0]}"/>
        <span class="scope-type"> ~ </span>
        <input class="scope-type" id="scopeEnd" type="text" value="${value.split(',')[1]}" />
        `)
    }

    $('.modal-footer').html(`
    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" onclick="editOption(${id})">Save</button>
    `)
    $('#newModalLabel').html('Edit option');
    $('#newFormElementPanel').modal('show');
    $('input:radio[name="standardType"]').click(() => {
        let checkValue = $('input:radio[name="standardType"]:checked').val();
        if (checkValue == '0') {
            $('.standard-value-container').html(`
            <label class="equal-type" for="equalType">Standard Value:</label>
            <input type="radio" name='equalType' value="1" />On
            <input type="radio" name='equalType' value="0" />Down
            `)
        } else if (checkValue == '1') {

            $('.standard-value-container').html(`
            <label class="scope-type" for="scopeType">Standard Value:</label>
            <input class="scope-type" id="scopeStart" type="text" /><span class="scope-type"> ~ </span><input class="scope-type" id="scopeEnd" type="text" />
            `)
        }
    });
}

let callDeleteModal = (name, id)=>{
    $('#deleteElementPanel').modal('show');
    // $('.delete-button').attr('onclick',`deleteStandard('${name}','${id}')`);
    $('.modal-footer').html(`
    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary delete-button" onclick="deleteStandard('${name}','${id}')">Confirm</button>
    `);
    console.log($('.delete-button'));
}
// delete option
let deleteStandard = (name, id)=>{
    console.log(name, id);
    let data = {'name':name, 'id':parseInt(id)}
    $.ajax({
        url: '/checkItemDelete',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (res) => {
            res=JSON.parse(res)
            $('#deleteElementPanel').modal('hide');
            if(typeof res == "string"){
                alert('res')
            }
            if(typeof res == "object"){
                mountOptions(res);

            }
        },
        error: (er) => {
            $('#deleteElementPanel').modal('hide');
            alert('fail to delete this element.');
            console.log('fail.')
        }
    })
}

// Mount options
let mountOptions = (customedOptions) => {
    let optionsStr = ``;
    customedOptions.map((ele, index) => {
        if (ele.mode == '0') {
            optionsStr = optionsStr + `
            <div>
                <input type="checkbox" name="optionSel" class="" value="${ele.name}"/>
                <label for="${ele.name}">${ele.name}: </label>
                <input type="radio" name="${ele.name}" value="${ele.value}" />On <input type="radio" name="${ele.name}" value="0" />Down <span
                        id="power_span" class="alert-span"></span>
                <i class="bi bi-pencil-square" onclick="callEditModal('${ele.name}', '${ele.mode}', '${ele.value}', '${ele.id}')"></i>
                <i class="bi bi-dash-square" onclick="callDeleteModal('${ele.name}', '${ele.id}')"></i><br />
            </div>
            `;
        } else if (ele.mode == '1') {
            optionsStr = optionsStr + `
            <div>
                <input type="checkbox" name="optionSel" value="${ele.name}"/>
                <label for="${ele.name}">${ele.name}: </label>
                <input name="${ele.name}" class="input-temperature" type="text" /><span>(Normal scape: ${JSON.parse(ele.value)[0]} -
                    ${JSON.parse(ele.value)[1]})</span><span id="temperature_span" class="alert-span"></span>
                <i class="bi bi-pencil-square" onclick="callEditModal('${ele.name}', '${ele.mode}', '${JSON.parse(ele.value)}', '${ele.id}')"></i>
                <i class="bi bi-dash-square" onclick="callDeleteModal('${ele.name}', '${ele.id}')"></i><br />
            </div>
            `;
        }
    })
    $('.options-container').html(optionsStr);
}

$(() => {
    initOptionList();
    // $('input:radio[name="standardType"]').click(() => {
    //     let checkValue = $('input:radio[name="standardType"]:checked').val();
    //     if (checkValue == '0') {
    //         $('.standard-value-container').html(`
    //         <label class="equal-type" for="equalType">Standard Value:</label>
    //         <input class="equal-type" type="text" />
    //         `)
    //         // $('.equal-type').show();
    //         // $('.scope-type').hide();
    //     } else if (checkValue == '1') {

    //         $('.standard-value-container').html(`
    //         <label class="scope-type" for="scopeType">Standard Value:</label>
    //         <input class="scope-type" id="scopeStart" type="text" /><span class="scope-type"> ~ </span><input class="scope-type" id="scopeEnd" type="text" />
    //         `)
    //         // $('.equal-type').hide();
    //         // $('.scope-type').show();
    //     }
    // });
})


//mocked data
let optionList = [{ name: 'power', type: '0', standard: '1' }, { name: 'fan', type: '0', standard: '1' }, { name: 'temperature', type: '3', standard: ['-10', '100'] }, { name: 'startTime', type: '3', standard: ['0', '10'] }]