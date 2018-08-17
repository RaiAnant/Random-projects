
async function loginTrueCaller(username, password) {
  console.log('going inside Tuecaller login function')
  try {
    
    await mainTab.goTo('https://www.truecaller.com')
    await mainTab.wait(4000)
    //await console.log( await mainTab.getCookies() )
    // await mainTab.waitForPageToLoad()
    // Click in logon / signup buttom -- Microsoft Sign in (google sign in opens new windows so facing issues)
    await mainTab.click('[class="TopNav__Link TopNav__SignIn"]')

    //await mainTab.click('#app > div.sign-in-dialog > div > div.sign-in-dialog-content > div:nth-child(3) > div')
    await mainTab.wait(2000)
    console.log('going to login')
    await mainTab.click('#app > div.sign-in-dialog >\
     div > div.sign-in-dialog-content > div:nth-child(3) > div > div.ltr')
    //await mainTab.click('[class="sign-in-dialog-provider"]')
    await mainTab.wait(5000)
    await mainTab.type('input[name="loginfmt"]',username)
    await mainTab.wait(2000)
    //await mainTab.click('#idSIButton9')
    await mainTab.click('input[type="submit"]')
    console.log('after submitting loginid')
    await mainTab.wait(5000)
    await mainTab.type('input[name="passwd"]',password)
    await mainTab.wait(1000)
    console.log('after submitting password')
    await mainTab.click('input[type="submit"]')
    console.log("#132")
    await mainTab.wait(8000)
    console.log("#134")
    //await console.log( await mainTab.getCookies() )
    return mainTab;

}
catch (err) {
    console.log('ERROR!', err)
}

}








async function processPages(username){ //comma separated phone numbers

  try{
    console.log("number is " + username)
    // Navigate a little...

    var phn_numbers = username.split(',')

    console.log("phn_numbers.length is " + phn_numbers.length)

    mainTab = await loginTrueCaller(usernamePwd[0], usernamePwd[1]);
    
    var fullDict={} 
    console.log("#626");
    //console.log('length of phn_numbers is ', phn_numbers);
    for (var i=0; i<phn_numbers.length; i++){
      users = phn_numbers[i].replace(/^\s*/, "").replace(/\s*$/, "").replace('+91','')
                .replace(/-/g,'').replace(/ /g,'').trim(); //It trims whitespace characters and removes +91
      await console.log("Scanning phone no.", users);
      const url = 'https://www.truecaller.com/search/in/' + users 
      
      await mainTab.goTo(url)
      await mainTab.wait(5000)
       // Name
      var ProfileName = await mainTab.evaluate(function(selector) {
      const selectorHtml = document.querySelector(selector)
                            return selectorHtml.innerHTML
                           }, '.ProfileName div');
      ProfileName =  JSON.stringify(ProfileName)
      var obj = JSON.parse(ProfileName)
      var name = (obj.result.value)
      
      //Pic

      var ProfilePic = await mainTab.evaluate(function(selector) {
        const selectorHtml = document.querySelector(selector)
                            return selectorHtml.innerHTML
                        }, '.ProfileAvatar');
      ProfilePic =  JSON.stringify(ProfilePic)
      var obj = JSON.parse(ProfilePic)
      var pic = (obj.result.value)
      //return pic

      //Tag

      var ProfileTag = await mainTab.evaluate(function(selector) {
      const selectorHtml = document.querySelector(selector)
                              return selectorHtml.innerHTML
                          }, '.ProfileTag .ProfileTag__Name');
      ProfileTag =  JSON.stringify(ProfileTag)
      var obj = JSON.parse(ProfileTag)
      var tag = (obj.result.value)


      //Phone

      var ProfileNo = await mainTab.evaluate(function(selector) {
                      const selectorHtml = document.querySelector(selector)
                      return selectorHtml.innerHTML
          }, '#app > div.profile.page > div > div.ProfileDetails\
           > div:nth-child(1) > div > div.List__Info > div:nth-child(2)');
      
      ProfileNo =  JSON.stringify(ProfileNo)
      var obj = JSON.parse(ProfileNo)
      var phone = (obj.result.value)
      console.log(phone);
      var email=''
      var location = ''
      var fbUrl = ''
      if (typeof phone != 'undefined'){
         //phone = phone.replace(/<div>/g,'').replace(/<\/div>/g,'');

         var child = await mainTab.evaluate(function(selector) {
                      const selectorHtml = document.querySelector(selector)
                      return selectorHtml.innerHTML
                  }, '#app > div.profile.page > div > div.ProfileDetails > div:nth-child(2)');

         child1 =  JSON.stringify(child)
         var obj = JSON.parse(child1)
         var child1 = (obj.result.value)
         //console.log("child1 is #688" + child1);
         var number  = child1.match(/href=/g)
         //console.log("number is #690" + number);
         var href1 = child1.match(/href="([^\'\"]+)/g);
         for (j =1 ; j<= number.length ; j++){
          //var href = child1.match(/href="([^"]*)/)[1];
          href = href1[j-1];
          var str = '#app > div.profile.page > div > div.ProfileDetails > div:nth-child(2) > a:nth-child(';
          var str2 = ') > div.List__Info > div';
          var res = str.concat(j, str2);

          var loc = await mainTab.evaluate(function(selector) {
            const selectorHtml = document.querySelector(selector)
            return selectorHtml.innerHTML
        }, res);
          console.log("loc is #700 "+ loc);
          Profileaddress =  JSON.stringify(loc)
          var obj = JSON.parse(Profileaddress)

          if (href.includes('www.google.com/maps')){
            location = (obj.result.value)
        }

        // else if (href.includes('www.facebook.com')){
        //     fbUrl = href.substring(6,);
        // }
        else{
            email = (obj.result.value)
        }
      }
}

     // var name = await getName(mainTab, url)
     // var tag = await getTag(mainTab, url)
     // var pic = await getPic(mainTab, url)
     // var phone = await getPhone(mainTab, url)
     // var email = await getMail(mainTab, url)
     // var location = await getLocation(mainTab, url)
          
    await mainTab.log("Inside main Function below scrollDown")
    await mainTab.wait(2000)

    var dict={}
    dict["PhoneEmail"]=users
    dict["Name"] = name
    dict["Tag"] = tag
    //dict["Pic"] = pic
    dict["Phone"] = phone
    dict["Email"] = email
    dict["Location"] = location
    dict["fbUrl"] = fbUrl
    // console.log(dict);
    fullDict[users] = dict
}

  await browser.close();
  // var fullDict = await searchFacebook(username,  filename, fullDict)
  await searchFacebook(username,  filename, fullDict, case_number, case_detail); //This is to be uncommented
  
  //csv = await printDict(username, fullDict, filename, case_number, case_detail);
  //console.log(fullDict)
    // Close the browser
}
catch (err) {
    console.log('ERROR!', err)
}

}

async function scrollDown(mainTab, numOfScrolls, fileName){

  try{
    if(numOfScrolls > 0){
      for (index = 0; index < numOfScrolls; index++) {
        await mainTab.wait(3000)
        await mainTab.log('Waiting 3 sec after each "End" event ' + index)
        await mainTab.keyboardEvent('keyUp', 'End',0,35)
        await mainTab.keyboardEvent('keyDown', 'End',0,35)
    }
}
else{
  console.log(await mainTab.savePdf(fileName,options={'printBackground':true})) 
}
console.log(await mainTab.savePdf(fileName,options={'printBackground':true}))
}

catch (err){
 console.log('Error inside function', err)
}
}
