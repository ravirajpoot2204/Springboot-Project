console.log("This is script file");
$("#bar").fadeIn();
$("#sidebar").hide();
$("#crossbtn").click(function () {
  console.log("Clicked");
  $("#sidebar").fadeOut();
});
$("#bar").click(function () {
  console.log("Clicked");
  $("#sidebar").fadeIn();
  $(".content").left(250);
});

$(document).ready(function () {
  // Hide the alert after 2.2 seconds
  setTimeout(() => {
    $(".alert").fadeOut(500); // Smooth fade-out effect over 500ms
  }, 2200);
});
function deleteContact(cid) {
  swal({
    title: "Are you sure?",
    text: "Do you want to delete this contact?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((willDelete) => {
    if (willDelete) {
      window.location = "/user/contact/delete/" + cid;
    } else {
      swal("Your contact is safe!");
    }
  });
}

// Sidebar toggle functionality
document.addEventListener("DOMContentLoaded", () => {
  const sidebar = document.getElementById("sidebar");
  const content = document.getElementById("content");
  const barBtn = document.getElementById("bar");
  const closeBtn = document.getElementById("closeSidebar");

  // Open sidebar
  barBtn.addEventListener("click", () => {
    sidebar.classList.add("show");
    content.classList.add("shift");
  });

  // Close sidebar
  closeBtn.addEventListener("click", () => {
    sidebar.classList.remove("show");
    content.classList.remove("shift");
  });
});

const search = () => {
  let query = $("#search-input").val();

  if (query != "") {
    console.log(query);

    let url = `http://localhost:7272/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        let text = `<div class='list-group'>`;

        data.forEach((c) => {
          text += `<a href='/user/contact/${c.cid}' class='list-group-item list-group-item-action'> ${c.name}</a>`;
        });

        text += `</div>`;

        $(".search-result").html(text);
        $(".search-result").show();
      });
  } else {
    $(".search-result").hide();
  }
};

//first request to server create order
const paymentStart=()=> {
  console.log("Payment Started....");
  let amount = $("#payField").val();
  console.log(amount);
  if (amount == "" || amount == null) {
    alert("amount is required");
    return;
  }

  //we will use ajax to send request to server to create order

  $.ajax({
    url: "/payment/create-Order",
    data: JSON.stringify({ amount: amount, info: "order_request" }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      //invoked when success
      console.log(response);
      if (response.status == 'created') {
        //open payment form
        let options = {
          key: "rzp_test_xdazwrEC2gX5qS",
          amount: response.amount,
          currency: "INR",
          name: "Smart Contact Manger",
          description: "Donation",
          image:
            "https://marketplace.canva.com/EAF0Hq4UHjM/1/0/1600w/canva-orange-phoenix-animal-gaming-logo-WIPEOAyYPIs.jpg",
          order_id: response.id,
          handler: function (response) {
            console.log(response.razorpay_payment_id);
            console.log(response.razorpay_order_id);
            console.log(response.razorpay_signature);
            console.log("Payment Was successfull!!");
            
            updatePaymentOnServer(
              response.razorpay_payment_id,
              response.razorpay_order_id,
              "paid"
            );


          },
          prefill: {
            //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
            name: "", //your customer's name
            email: "",
            contact: "", //Provide the customer's phone number for better conversion rates
          },
          notes: {
            address: "Testing",
          },
          theme: {
            color: "#3399cc",
          },
        };
        let rzp = new Razorpay(options);
	

        rzp.on("payment.failed", function (response) {
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          alert("");
          });
		rzp.open();
		
      }
    },
    error: function (error) {
      //invoked when error occurs
      console.log(error);
      alert("something went wrong!");
    },
  });
};

//update payment function

const updatePaymentOnServer=(payment_id,order_id,status)=>
{
	
  $.ajax({
    url: "/payment/update-Order",
    data: JSON.stringify({ payment_id: payment_id, order_id: order_id,status:status }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",

    success:function(response){
      swal("Appreciated!!","Congractulations! Your Payment Was Successfull !","success");
       
    },
    error:function(error){

      swal("Alas!",
        "Your payment is pending.. if you don't get your money back in case of failed contact us!!"
        ,"warning");
    }
});
};